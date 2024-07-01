package com.sparta.delivery_app.common.globalcustomexception.store;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalMismatchException;

public class StoreMenuMismatchException extends GlobalMismatchException {
    public StoreMenuMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
