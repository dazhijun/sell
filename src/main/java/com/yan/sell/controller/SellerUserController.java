package com.yan.sell.controller;

import com.yan.sell.config.ProjectUrlConfig;
import com.yan.sell.constant.CookieConstant;
import com.yan.sell.constant.RedisConstant;
import com.yan.sell.dataobject.SellerInfo;
import com.yan.sell.enums.ResultEnum;
import com.yan.sell.service.SellerService;
import com.yan.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig  urlConfig;

    @Resource
    private SellerService sellerService;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //openid和数据库里的数据匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessgae());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //设置token至redis
        String token= UUID.randomUUID().toString();
        log.error("==========redis_token:"+token+"============");
        Integer expir= RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expir, TimeUnit.SECONDS);
        //设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expir);
        return new ModelAndView("redirect:"+urlConfig.getWechatMpAuthorize()+"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //从cookie里查询
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null){
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            log.error("==========logout操作，清除了cookie===========");
        }
        //清除cookie
        CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessgae());
        return new ModelAndView("common/success");
    }
    @GetMapping("/test1")
    public ModelAndView test1(HttpServletRequest request,
                              HttpServletResponse response){
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        return new ModelAndView("common/success");
    }

    @GetMapping("/test2")
    public ModelAndView test3(){
        return new ModelAndView("common/success");
    }
}
