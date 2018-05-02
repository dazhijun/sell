package com.yan.sell.enums;

import lombok.Getter;
import org.hibernate.criterion.Order;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISH(1,"完结"),
    CANCEL(2,"已取消");
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
