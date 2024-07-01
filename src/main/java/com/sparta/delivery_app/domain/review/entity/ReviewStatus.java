package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.review.ReviewStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    private final String reviewStatusName;

    public static void checkReviewStatus(UserReviews userReviews) {
        if (userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }
    }
}
