package com.sparta.delivery_app.domain.store.entity;

import com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.store.StoreNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    ENABLE("ENABLE"),
    DISABLE("DISABLE");

    private final String storeStatusName;

    /**
     * 매장 상태 검증
     * @param store
     */
    public static void checkStoreStatus(Store store) {
        if(store.getStatus().equals(StoreStatus.DISABLE)) {
            throw new StoreNotFoundException(StoreErrorCode.DELETED_STORE);
        }
    }

}
