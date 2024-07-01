package com.sparta.delivery_app.common.globalcustomexception.review;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalStatusException;

public class ReviewStatusException extends GlobalStatusException {
    public ReviewStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
