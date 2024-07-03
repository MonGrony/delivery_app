package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.common.exception.errorcode.ThanksErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.thanks.ThanksAlreadyException;
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
    public void addOrDeleteThanks(AuthenticationUser user, Long reviewId) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //도움이 돼요 목록 조회
    public Page<Thanks> getThanksList(AuthenticationUser user, Integer pageNum, Boolean isDesc) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        Pageable pageable = PageUtil.createPageable(pageNum, PAGE_SIZE_FIVE, isDesc);

        //5개씩 페이징 처리한 thanks List(최신순) 가지고 오기
        return thanksAdapter.queryUserThanksPage(findUser,pageable);
    }

        // 응답정보는 댓글 목록조회(없음)의 응답정보와 동일

        //프로필에 내가 좋아요한 게시글 수/댓글 수 응답필드 추가하기

        //리뷰 단 건 조회시 몇 개나 도움이 된다고 눌렸는지 (몇 명이 눌렀는지) 알 수 있도록 개수필드 추가

        // 리뷰 service 에서 예정인 것
        // 리뷰가 삭제될 경우 리뷰 자체 status 는 바뀌니까 상관없는데
        // profile 에서 조회시 안 보이게 해야함 (review status 에 따라 반영)
        // 테스트 코드 작성 - queryDSL
        // DB 접근은 queryDsl 이용 - testCode 에서 / repository 도?


}

