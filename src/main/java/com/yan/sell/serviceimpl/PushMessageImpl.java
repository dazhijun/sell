package com.yan.sell.serviceimpl;

import com.yan.sell.config.WechatAccountConfig;
import com.yan.sell.dto.OrderDTO;
import com.yan.sell.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PushMessageImpl implements PushMessage{

    @Autowired
    private WechatAccountConfig accountConfig;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage=new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data= Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","柴米油盐"),
                new WxMpTemplateData("keyword2","18720037015"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",""+orderDTO.getOrderStatus()),
                new WxMpTemplateData("keyword5",orderDTO.getOrderAmount()+""),
                new WxMpTemplateData("remark","亲，欢迎再次光临")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getAccessToken();
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】 发送失败,{}",e);
        }
    }
}
