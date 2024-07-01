package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewAccessDeniedException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewStatusException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.order.adapter.OrderAdapter;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.review.adapter.ManagerReviewsAdapter;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerReviewsService {

    private final ManagerReviewsAdapter managerReviewsAdapter;
    private final OrderAdapter orderAdapter;
    private final UserAdapter userAdapter;

    public ManagerReviewAddResponseDto addReview(final Long orderId, final ManagerReviewAddRequestDto requestDto, AuthenticationUser user) {
        // 사용자 존재 확인
        User userData = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        // 주문 존재 확인
        Order orderData = orderAdapter.queryOrderById(orderId);
        if (!orderData.getStore().getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_CREATED_REVIEW);
        }

        // 배달 상태 확인
        OrderStatus.checkOrderStatus(orderData);

        // 사용자 리뷰가 존재하는지 확인
        UserReviews userReviews = orderData.getUserReviews();
        if (userReviews == null) {
            throw new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW);
        }
        if (userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }

        // 매니저 리뷰 존재 확인
        ManagerReviews managerReviews = userReviews.getManagerReviews();
        if (managerReviews != null) {
            throw new ReviewDuplicatedException(ReviewErrorCode.REVIEW_ALREADY_REGISTERED_ERROR);
        }

        ManagerReviews savedManagerReviews = ManagerReviews.saveManagerReview(userReviews, userData, requestDto);

        managerReviewsAdapter.saveReview(savedManagerReviews);

        return ManagerReviewAddResponseDto.of(savedManagerReviews);
    }

    @Transactional
    public ManagerReviewModifyResponseDto modifyReview(final Long orderId, final ManagerReviewModifyRequestDto requestDto, AuthenticationUser user) {
        // 사용자 존재 확인
        User userData = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        // 주문 존재 확인
        Order orderData = orderAdapter.queryOrderById(orderId);

        // 가게 사장과 유저가 같은 사람인지 확인( 사장=유저(manager) 여야함)
        if (!orderData.getStore().getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_UPDATE_REVIEW);
        }

        // Order를 통해 리뷰ID 존재 확인
        UserReviews userReviews = orderData.getUserReviews();
        if (userReviews == null) {
            throw new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW);
        }

        // 매니저 리뷰 존재 확인
        ManagerReviews managerReviews = userReviews.getManagerReviews();
        if (managerReviews == null) {
            throw new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW);
        }

        // 수정권한 확인
        if (!managerReviews.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_UPDATE_REVIEW);
        }

        ManagerReviews modifiedManagerReviews = managerReviews.modifyManagerReview(requestDto);

        return ManagerReviewModifyResponseDto.of(modifiedManagerReviews);
    }
}
