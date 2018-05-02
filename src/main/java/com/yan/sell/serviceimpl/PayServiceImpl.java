package com.yan.sell.serviceimpl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.yan.sell.dto.OrderDTO;
import com.yan.sell.enums.ResultEnum;
import com.yan.sell.exception.SellException;
import com.yan.sell.service.OrderService;
import com.yan.sell.service.PayService;
import com.yan.sell.util.JsonUtil;
import com.yan.sell.util.Mathutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

@Service
@Slf4j
public class PayServiceImpl implements PayService{
    private static final String ORDER_NAME="微信点餐系统";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.error("【微信支付】request={}", JsonUtil.tojson(payRequest));
        PayResponse payResponse=bestPayService.pay(payRequest);
        log.error("【微信支付】response={}",JsonUtil.tojson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //验证签名
        //支付状态
        //支付金额
        //支付人
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.error("【微信支付】 异步通知，payresponse={}",JsonUtil.tojson(payResponse));

        //修改订单支付状态
        OrderDTO orderDTO=orderService.findOne(payResponse.getOrderId());
        if (orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致
        if (!Mathutil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】 异步通知，订单金额不一致");
        }
        orderService.paid(orderDTO);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.error("【微信退款】 request={}",refundRequest);

        RefundResponse refundResponse=bestPayService.refund(refundRequest);
        log.error("【微信退款】 response={}",refundResponse);
        return refundResponse;
    }
}