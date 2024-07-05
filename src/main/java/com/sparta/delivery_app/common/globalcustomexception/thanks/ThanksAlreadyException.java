package com.sparta.delivery_app.common.globalcustomexception.thanks;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalAccessDeniedException;

public class ThanksAlreadyException extends GlobalAccessDeniedException {
    public ThanksAlreadyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
