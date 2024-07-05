package com.sparta.delivery_app.common.globalcustomexception.thanks;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalAccessDeniedException;

public class ThanksSelfException extends GlobalAccessDeniedException {
    public ThanksSelfException(ErrorCode errorCode) {
        super(errorCode);
    }
}
