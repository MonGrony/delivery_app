package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThanksErrorCode implements ErrorCode{
    THANKS_ONLY_BY_OTHERS(StatusCode.UNAUTHORIZED.getCode(), "다른 사람의 리뷰에만 가능합니다."),
    ALREADY_THANKS(StatusCode.METHOD_NOT_ALLOWED.getCode(), "좋아요가 취소되었습니다."),
    THANKS_UNREGISTERED_ERROR(StatusCode.BAD_REQUEST.getCode(), "등록되지 않은 관심 매장입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
