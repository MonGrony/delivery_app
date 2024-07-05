package com.sparta.delivery_app.common.globalcustomexception.order;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalAccessDeniedException;

public class OrderAccessDeniedException extends GlobalAccessDeniedException {
    public OrderAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
