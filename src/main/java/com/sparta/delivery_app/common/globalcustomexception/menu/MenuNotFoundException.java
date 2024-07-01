package com.sparta.delivery_app.common.globalcustomexception.menu;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.global.GlobalNotFoundException;

public class MenuNotFoundException extends GlobalNotFoundException {
    public MenuNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
