package com.yan.sell.controller;

import com.yan.sell.service.RedisLock;
import com.yan.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test/lock")
public class TestLockController {

    private int Total=10000;

    private Map<String,String> map=new HashMap<>();

    private int rest;

    @Autowired
    private RedisLock redisLock;

    @GetMapping("/getOne")
    public void getOne(){
        map.put(KeyUtil.genUniqueKey(),"好东西");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rest++;
        Total--;
        System.out.println("=======一共有10000个，现在还有"+Total+"个"+"已经被拿走"+rest+"个名额"+"|实际被选名额有"+map.size()+"个");
    }


}
