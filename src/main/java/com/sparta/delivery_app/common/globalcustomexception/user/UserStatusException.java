package com.sparta.delivery_app.common.globalcustomexception.user;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalStatusException;

public class UserStatusException extends GlobalStatusException {
    public UserStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
