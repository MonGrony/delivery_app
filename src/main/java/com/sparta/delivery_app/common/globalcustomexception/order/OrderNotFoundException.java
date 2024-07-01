package com.sparta.delivery_app.common.globalcustomexception.order;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalNotFoundException;

public class OrderNotFoundException extends GlobalNotFoundException {
    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
