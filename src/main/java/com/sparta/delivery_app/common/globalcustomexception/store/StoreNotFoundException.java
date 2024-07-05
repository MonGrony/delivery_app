package com.sparta.delivery_app.common.globalcustomexception.store;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalNotFoundException;

public class StoreNotFoundException extends GlobalNotFoundException {
    public StoreNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
