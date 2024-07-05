package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewAccessDeniedException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.S3Exception;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.order.adapter.OrderAdapter;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.dto.SpecificReviewElements;
import com.sparta.delivery_app.domain.review.dto.SpecificReviewResponseDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.s3.service.S3Uploader;
import com.sparta.delivery_app.domain.s3.util.S3Utils;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.thanks.adapter.ThanksAdapter;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReviewsService {

    private final UserReviewsAdapter userReviewsAdaptor;
    private final OrderAdapter orderAdaptor;
    private final UserAdapter userAdaptor;
    private final S3Uploader s3Uploader;
    private final ThanksAdapter thanksAdapter;
    private final StoreAdapter storeAdapter;


    @Transactional
    public UserReviewAddResponseDto addReview(MultipartFile file, final Long orderId, final UserReviewAddRequestDto requestDto, AuthenticationUser user) {
        // 사용자 확인
        User userData = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        // 주문이 등록되어있는가? -> 주문이 없다면 예외
        Order order = orderAdaptor.queryOrderById(orderId);
        if (!order.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_CREATED_REVIEW);
        }

        // 배달 상태 확인
        OrderStatus.checkOrderStatus(order);

        // 주문에 이미 리뷰가 존재하는지 확인
        UserReviews userReviews = order.getUserReviews();
        if (userReviews != null) {
            throw new ReviewDuplicatedException(ReviewErrorCode.REVIEW_ALREADY_REGISTERED_ERROR);
        }

        UserReviews savedReview = UserReviews.saveReview(order, userData, requestDto);

        userReviewsAdaptor.saveReview(savedReview);

        if (S3Utils.isFileExists(file)) {
            try {
                String reviewImagePath = s3Uploader.saveReviewImage(file, userData.getId(), savedReview.getId());
                savedReview.updateReviewImagePath(reviewImagePath);
            } catch(S3Exception e) {
                userReviewsAdaptor.deleteTempReview(savedReview);
                throw new S3Exception(e.getErrorCode());
            }
        }

        return UserReviewAddResponseDto.of(savedReview);
    }

    @Transactional
    public UserReviewModifyResponseDto modifyReview(MultipartFile file, final Long orderId, final UserReviewModifyRequestDto requestDto, AuthenticationUser user) {
        // 사용자 확인
        User userData = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        // 주문이 등록되어있는가? -> 주문이 없다면 예외
        Order order = orderAdaptor.queryOrderById(orderId);
        if (!order.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_UPDATE_REVIEW);
        }

        // 사용자 리뷰가 있는지 확인
        UserReviews userReviews = order.getUserReviews();
        if (userReviews == null) {
            throw new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW);
        }

        // 수정권한 확인
        if (!userReviews.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_UPDATE_REVIEW);
        }

        // 사용자 리뷰가 삭제되어 있을때 예외
        ReviewStatus.checkReviewStatus(userReviews);

        if (S3Utils.isFileExists(file)) {
            try {
                String menuImagePath = s3Uploader.saveReviewImage(file, userData.getId(), userReviews.getId());
                userReviews.updateReviewImagePath(menuImagePath);
            } catch(S3Exception e) {
                throw new S3Exception(e.getErrorCode());
            }
        }

        // 리뷰 업데이트(return this 사용)
        UserReviews updatedReview = userReviews.updateReview(requestDto);

        //entity -> Dto 로 변환 후 리턴
        return UserReviewModifyResponseDto.of(updatedReview);
    }

    @Transactional
    public void deleteReview(final Long reviewId, AuthenticationUser user) {

        // 사용자 확인
        User userData = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        // 리뷰ID 존재하는지 확인 + 이미 삭제가 되었는지 확인
        UserReviews userReviews = userReviewsAdaptor.findValidUserReview(reviewId);

        if (!userReviews.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_DELETE_REVIEW);
        }

        userReviews.deleteReview();
    }

    public SpecificReviewResponseDto getSpecificReview(AuthenticationUser user, Long reviewId) {
        //로그인 유저 검증
        userAdaptor.queryUserByEmailAndStatus(user.getUsername());
        //리뷰 검증
        UserReviews findReview = userReviewsAdaptor.findValidUserReview(reviewId);
        //리뷰 작성자
        User reviewer = findReview.getUser();
        //리뷰 출처 매장
        Store findStore = storeAdapter.queryStoreByReviewId(reviewId);
        //responseDto 채우기
        SpecificReviewElements elements = setElements(findReview, reviewer, findStore);

        return SpecificReviewResponseDto.of(elements);
    }

    private SpecificReviewElements setElements(UserReviews findReview, User reviewer, Store findStore) {
        return new SpecificReviewElements(
                findReview.getContent(),
                findReview.getReviewImagePath(),
                findReview.getRating(),
                findStore.getId(),
                findReview.getUser().getNickName(),
                thanksAdapter.queryAllThanksCountByUser(reviewer)
        );
    }
}
