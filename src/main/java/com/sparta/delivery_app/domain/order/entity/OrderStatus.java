package com.sparta.delivery_app.domain.order.entity;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.order.OrderStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_COMPLETED("ORDER_COMPLETED"),
    IN_PREPARATION("IN_PREPARATION"),
    DELIVERY_COMPLETED("DELIVERY_COMPLETED");

    private final String orderStatusName;

    public static void checkOrderStatus(Order orderData) {
        if (!orderData.getOrderStatus().equals(DELIVERY_COMPLETED)) {
            throw new OrderStatusException(OrderErrorCode.DELIVERY_NOT_COMPLATED);
        }

    }
}
