package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.service.UserReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews/consumers")
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    @PostMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewAddResponseDto>> create(
            @PathVariable final Long orderId,
            @Valid @RequestBody final UserReviewAddRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewAddResponseDto responseDto = userReviewsService.addReview(orderId, requestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewModifyResponseDto>> update(
            @PathVariable final Long orderId,
            @Valid @RequestBody final UserReviewModifyRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewModifyResponseDto responseDto = userReviewsService.modifyReview(orderId, requestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> delete(
            @PathVariable final Long reviewId,
            @AuthenticationPrincipal AuthenticationUser user) {

        userReviewsService.deleteReview(reviewId, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 삭제되었습니다."));
    }
}
