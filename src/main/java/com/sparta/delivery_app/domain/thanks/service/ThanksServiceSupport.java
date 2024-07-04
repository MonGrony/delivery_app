package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.common.exception.errorcode.ThanksErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksAlreadyException;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThanksServiceSupport {

    //  loginUser 의 도움이 돼요 list 에 이미 등록된 review 인지 확인 -> 취소 or 등록 적용
    public static void addOrCancel(User loginUser, Long reviewId, UserReviews findUserReview) {

        try {
            Thanks enrolledThanks = loginUser.getThanksList().stream()
                    .filter(thanks -> thanks.getUserReviews().getId().equals(reviewId))
                    .findFirst().orElseThrow(
                            () -> new ThanksAlreadyException(ThanksErrorCode.ALREADY_THANKS));

            Thanks.cancelThanks(loginUser, enrolledThanks);
            log.info("thanks 취소 완료");

        } catch (ThanksAlreadyException e) {
            log.info("thanks 등록 진입");
                Thanks thanks = Thanks.builder()
                        .userReviews(findUserReview)
                        .user(loginUser)
                        .build();

                loginUser.getThanksList().add(thanks);

        } catch (Exception e) {
            log.error("exception");
            e.printStackTrace();
        }
    }
}
