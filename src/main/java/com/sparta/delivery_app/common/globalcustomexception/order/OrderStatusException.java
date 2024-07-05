package com.sparta.delivery_app.common.globalcustomexception.order;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalStatusException;

public class OrderStatusException extends GlobalStatusException {
    public OrderStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
