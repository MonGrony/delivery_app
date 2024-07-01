package com.sparta.delivery_app.common.globalcustomexception.store;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalDuplicatedException;

public class StoreDuplicatedException extends GlobalDuplicatedException {

    public StoreDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
