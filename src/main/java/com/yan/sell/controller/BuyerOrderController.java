package com.yan.sell.controller;

import com.yan.sell.VO.ResultVO;
import com.yan.sell.converter.OrderForm2OrderDTOConverter;
import com.yan.sell.converter.OrderMaster2OrderDTOConverter;
import com.yan.sell.dto.OrderDTO;
import com.yan.sell.enums.ResultEnum;
import com.yan.sell.exception.SellException;
import com.yan.sell.form.OrderForm;
import com.yan.sell.service.BuyerService;
import com.yan.sell.service.OrderService;
import com.yan.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
/*    其次在controller层的方法的要校验的参数上添加@Valid注解，
     并且需要传入BindingResult对象，
     用于获取校验失败情况下的反馈信息，如下代码：*/
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,BindingResult bindingResult ){
        System.out.println(orderForm.toString());
        if (bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确,orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO= OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult=orderService.create(orderDTO);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value= "page",defaultValue ="0") Integer page,
                                         @RequestParam(value= "size",defaultValue ="10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "openid",required = false) String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，需要改进（不是所有人都能访问,通过openid进行判断）
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，需要改进（不是所有人都能访问,通过openid进行判断）
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
