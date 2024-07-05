package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.common.exception.errorcode.ThanksErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksSelfException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.thanks.adapter.ThanksAdapter;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sparta.delivery_app.domain.commen.page.util.PageUtil.PAGE_SIZE_FIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThanksService {

    private final UserReviewsAdapter userReviewsAdapter;
    private final UserAdapter userAdapter;
    private final ThanksAdapter thanksAdapter;

    // 로그인한 유저가 고르다가 도움이 되는 리뷰를 보고 도움이 돼요를 누른 상태
    public void addOrCancelThanks(AuthenticationUser user, Long reviewId) {

        UserReviews findUserReview = userReviewsAdapter.findValidUserReview(reviewId);
        User loginUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        User reviewUser = findUserReview.getUser();

        checkUserHimself(reviewUser, loginUser);
        ThanksServiceSupport.addOrCancel(loginUser, reviewId, findUserReview);
    }

    //도움이 돼요 목록 조회
    public Page<Thanks> getThanksList(AuthenticationUser user, Integer pageNum, Boolean isDesc) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        Pageable pageable = PageUtil.createPageable(pageNum, PAGE_SIZE_FIVE, isDesc);

        //5개씩 페이징 처리한 thanks List(최신순) 가지고 오기
        return thanksAdapter.queryUserThanksPage(findUser, pageable); //null 체크 필요 isNull
    }

    // 동일인인지 확인 -> 동일인이면 등록 불가
    private void checkUserHimself(User reviewUser, User loginUser) {
        if (reviewUser.getId().equals(loginUser.getId())) {
            throw new ThanksSelfException(ThanksErrorCode.THANKS_ONLY_BY_OTHERS);
        }
    }

}

