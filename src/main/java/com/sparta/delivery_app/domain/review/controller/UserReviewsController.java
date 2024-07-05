package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.SpecificReviewResponseDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.service.UserReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews/consumers")
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewAddResponseDto>> create(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable final Long orderId,
            @Valid @RequestPart final UserReviewAddRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewAddResponseDto responseDto = userReviewsService.addReview(file, orderId, requestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PatchMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewModifyResponseDto>> update(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable final Long orderId,
            @Valid @RequestPart final UserReviewModifyRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewModifyResponseDto responseDto = userReviewsService.modifyReview(file, orderId, requestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> delete(
            @PathVariable final Long reviewId,
            @AuthenticationPrincipal AuthenticationUser user) {

        userReviewsService.deleteReview(reviewId, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 삭제되었습니다."));
    }

    /**
     * 리뷰별 단건 조회 -> 도움이 돼요 를 몇 명이 눌렀는지 확인 가능
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> getSpecificReview(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long reviewId) {

        SpecificReviewResponseDto ResponseDto = userReviewsService.getSpecificReview(user, reviewId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("성공",ResponseDto));
    }
}
