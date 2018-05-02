package com.yan.sell.aspect;

import com.yan.sell.constant.CookieConstant;
import com.yan.sell.constant.RedisConstant;
import com.yan.sell.exception.SellException;
import com.yan.sell.exception.SellerAuthorizeException;
import com.yan.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate template;

    @Pointcut("execution(public * com.yan.sell.controller.Sell*.*(..))"+
            "&& !execution(public * com.yan.sell.controller.SellerUserController.*(..))")
    public void verify(){

    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //查询cookie
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie==null){
            log.error("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //去redis中查询
        String tokenValue=template.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.error("【登录校验】redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
