package com.sparta.delivery_app.domain.user.entity;

import com.sparta.delivery_app.common.exception.errorcode.UserErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.UnableOpenStoreException;
import com.sparta.delivery_app.common.globalcustomexception.user.UserStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.sparta.delivery_app.common.exception.errorcode.UserErrorCode.NOT_AUTHORITY_TO_REGISTER;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    private final String userStatusName;

    public static void checkUserStatus(UserStatus userRole) {
        if (userRole == DISABLE) {
            throw new UserStatusException(UserErrorCode.USER_STATUS_DISABLE);
        }
    }

    public static void checkManagerEnable(User user) {
        if (!user.getUserStatus().equals(UserStatus.ENABLE)) {
            throw new UnableOpenStoreException(NOT_AUTHORITY_TO_REGISTER);
        }
    }
}
