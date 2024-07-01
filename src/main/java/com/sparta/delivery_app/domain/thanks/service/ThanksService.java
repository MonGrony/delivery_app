package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.common.exception.errorcode.ThanksErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksAlreadyException;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksSelfException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThanksService {

    private final UserReviewsAdapter userReviewsAdapter;
    private final UserAdapter userAdapter;

    // 로그인한 유저가 고르다가 도움이 되는 리뷰를 보고 도움이 돼요를 누른 상태
    public void addThanks(AuthenticationUser user, Long reviewId) {

        UserReviews findUserReview = userReviewsAdapter.findValidUserReview(reviewId);
        User loginUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        User reviewUser = findUserReview.getUser();
        // 동일인인지 확인 -> 동일인이면 등록 불가
        if (reviewUser.getId().equals(loginUser.getId())) {
            throw new ThanksSelfException(ThanksErrorCode.THANKS_ONLY_BY_OTHERS);
        }
        //  loginUser 의 도움이 돼요 list 에 이미 등록된 review 인지 확인 -> 취소 or 등록 적용

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
        }
    }


    // 리뷰 service 에서 예정
    // 리뷰가 삭제될 경우 리뷰 자체 status 는 바뀌니까 상관없는데
    // 도움이돼요 Status 또한 바뀌도록 해서 profile 에서 조회시 안 보이게 해야함

    // 도움이돼요 list 에 추가됨
    // 도움이 돼요 list 조회시 페이징 처리
    // 테스트 코드 작성 - queryDSL
}

