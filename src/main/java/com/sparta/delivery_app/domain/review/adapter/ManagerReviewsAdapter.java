package com.sparta.delivery_app.domain.review.adapter;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewStatusException;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.ManagerReviewsStatus;
import com.sparta.delivery_app.domain.review.repository.ManagerReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerReviewsAdapter {

    private final ManagerReviewsRepository managerReviewsRepository;

    public void saveReview(ManagerReviews managerReviews) {
        managerReviewsRepository.save(managerReviews);
    }

    public ManagerReviews checkValidManagerReviewByIdAndManagerReviewStatus(Long reviewId) {
        ManagerReviews managerReviews = findById(reviewId);

        if(managerReviews.getManagerReviewsStatus().equals(ManagerReviewsStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }

        return managerReviews;
    }

    public ManagerReviews findById(Long reviewId) {
        return managerReviewsRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }

}
