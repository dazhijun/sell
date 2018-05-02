package com.yan.sell.service;

import com.yan.sell.dto.OrderDTO;

public interface PushMessage {
    //订单状态变更消息
    void orderStatus(OrderDTO orderDTO);
}
