package com.yan.sell.handler;

import com.yan.sell.VO.ResultVO;
import com.yan.sell.config.ProjectUrlConfig;
import com.yan.sell.exception.ResponseBankException;
import com.yan.sell.exception.SellException;
import com.yan.sell.exception.SellerAuthorizeException;
import com.yan.sell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

/*
全局异常处理
配合@ExceptionHandler使用
能够拦截项目中所有指定异常
 */
@Slf4j
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    //拦截登录异常

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        log.error("【卖家错误处理】错误：登录过期");
        return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatMpAuthorize())
        .concat("/sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlConfig.getSell())
        .concat("/sell/seller/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseBankException(ResponseBankException e){
        System.out.println("来到这里了");
    }

}
