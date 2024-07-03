package com.sparta.delivery_app.domain.thanks.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.thanks.service.ThanksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 도움이 돼요 등록 또는 취소
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> addOrDeleteThanks(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long reviewId) {

        thanksService.addOrDeleteThanks(user, reviewId);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("도움이 된 리뷰로 등록되었습니다."));
    }

    /**
     * 개인별 도움이 돼요 누른 전체(리스트) 보기
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping
    public ResponseEntity<RestApiResponse<Page<Thanks>>> getThanksList(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc
    ) {
        Page<Thanks> userThankList = thanksService.getThanksList(user, pageNum,isDesc);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("성공", userThankList));
    }

    /**
     * 리뷰별 단건 조회 -> 도움이 돼요 를 몇 명이 눌렀는지 확인 가능
     */
}
