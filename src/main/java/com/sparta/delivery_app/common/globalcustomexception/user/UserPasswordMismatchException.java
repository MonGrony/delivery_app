package com.sparta.delivery_app.common.globalcustomexception.user;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalMismatchException;

public class UserPasswordMismatchException extends GlobalMismatchException {
    public UserPasswordMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
