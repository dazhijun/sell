package com.yan.sell.serviceimpl;

import com.yan.sell.dataobject.OrderDetail;
import com.yan.sell.dto.OrderDTO;
import com.yan.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Test
    public void findList() {
        Pageable p=new PageRequest(1,2);
        Page<OrderDTO> orderDTOPage=orderService.findList("111",p);
        System.out.println(orderDTOPage.getTotalElements());
        log.info("测试orderserviceimpl中的findlist方法");
    }

    @Test
    public void create(){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerAddress("街心花园二十三号");
        orderDTO.setBuyerPhone("18720037015");
        orderDTO.setBuyerOpenid("ew3euwhd7sjw9diwkq");
        List<OrderDetail> details=new ArrayList<>();
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(3);
        details.add(orderDetail);
        OrderDetail orderDetai2=new OrderDetail();
        orderDetai2.setProductId("2");
        orderDetai2.setProductQuantity(3);
        details.add(orderDetai2);
        orderDTO.setOrderDetailList(details);
        orderService.create(orderDTO);

    }

    @Test
    public void cancel(){
        OrderDTO orderDTO=orderService.findOne("1516006754602723074");
        OrderDTO o1=orderService.cancel(orderDTO);
        System.out.println(o1);
    }

    @Test
    public void list(){
        PageRequest request=new PageRequest(2,1);
        Page<OrderDTO> orderDTOPage=orderService.findList(request);
        assertNotEquals(0,orderDTOPage.getTotalElements());
    }

}