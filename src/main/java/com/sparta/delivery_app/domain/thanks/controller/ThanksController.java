package com.sparta.delivery_app.domain.thanks.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.thanks.service.ThanksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/thanks")
public class ThanksController {

    /**
     * (리뷰가) 도움이 돼요 관련 기능
     */

    private final ThanksService thanksService;

    /**
     * 도움이 돼요 등록
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> addThanks(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long reviewId){

        thanksService.addThanks(user, reviewId);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("도움이 된 리뷰로 등록되었습니다."));
    }

    /**
     * 도움이 돼요 취소
     */

    /**
     * 도움이 돼요 전체(리스트) 보기
     */


}
