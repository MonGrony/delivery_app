package com.sparta.delivery_app.common.globalcustomexception.user;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalDuplicatedException;

public class UserDuplicatedException extends GlobalDuplicatedException {

    public UserDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
