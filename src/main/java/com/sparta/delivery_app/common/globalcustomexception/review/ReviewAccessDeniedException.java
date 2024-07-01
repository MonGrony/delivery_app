package com.sparta.delivery_app.common.globalcustomexception.review;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalAccessDeniedException;

public class ReviewAccessDeniedException extends GlobalAccessDeniedException {
    public ReviewAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
