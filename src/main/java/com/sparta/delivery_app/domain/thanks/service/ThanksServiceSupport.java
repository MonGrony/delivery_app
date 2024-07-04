package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.common.exception.errorcode.ThanksErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksAlreadyException;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.entity.User;

public class ThanksServiceSupport {

    //  loginUser 의 도움이 돼요 list 에 이미 등록된 review 인지 확인 -> 취소 or 등록 적용
    public static void addOrCancel(User loginUser, Long reviewId, UserReviews findUserReview) {
        boolean isCanceled = false;

        try {
            Thanks enrolledThanks = loginUser.getThanksList().stream()
                    .filter(thanks -> thanks.getUserReviews().getId()
                            .equals(reviewId)).findFirst().orElseThrow(
                            () -> new ThanksAlreadyException(ThanksErrorCode.ALREADY_THANKS));

            Thanks.cancelThanks(loginUser, enrolledThanks);

        } catch (ThanksAlreadyException e) {

            if (!isCanceled) {
                Thanks thanks = Thanks.builder()
                        .userReviews(findUserReview)
                        .user(loginUser)
                        .build();
                loginUser.getThanksList().add(thanks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
