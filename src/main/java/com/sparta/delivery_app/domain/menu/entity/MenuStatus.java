package com.sparta.delivery_app.domain.menu.entity;

import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.menu.MenuNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    private final String menuStatusName;

    /**
     * 메뉴 상태 검증
     * @param menu
     */
    public static void checkMenuStatus(Menu menu) {
        if(menu.getMenuStatus().equals(MenuStatus.DISABLE)) {

            throw new MenuNotFoundException(MenuErrorCode.DELETED_MENU);
        }
    }
}
