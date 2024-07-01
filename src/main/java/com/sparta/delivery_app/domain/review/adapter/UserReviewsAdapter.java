package com.sparta.delivery_app.domain.review.adapter;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewStatusException;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewsAdapter {

    private final UserReviewsRepository userReviewsRepository;

    /**
     * 리뷰 등록
     */
    public void saveReview(UserReviews userReviews) {
        userReviewsRepository.save(userReviews);
    }

    /**
     * 리뷰 id로 유효한 리뷰 찾기 (리뷰 Status 검증 기능 포함)
     */
    public UserReviews findValidUserReview(Long reviewId) {
        UserReviews userReviews = findById(reviewId);
        checkValidByStatus(userReviews);

        return userReviews;
    }

    /**
     * 리뷰 Status 검증
     */
    private void checkValidByStatus(UserReviews userReviews) {
        if(userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }
    }

    /**
     * 리뷰 Id로 리뷰 찾기
     */
    private UserReviews findById(Long reviewId) {
        return userReviewsRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }

    /**
     * 이미지 업로드 중 문제 발생 시 임시 등록한 리뷰 삭제
     */
    public void deleteTempReview(UserReviews tempReview) {
        userReviewsRepository.delete(tempReview);
    }
}
