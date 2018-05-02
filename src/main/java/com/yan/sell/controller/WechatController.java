package com.yan.sell.controller;

import com.yan.sell.config.ProjectUrlConfig;
import com.yan.sell.config.WechatAccountConfig;
import com.yan.sell.enums.ResultEnum;
import com.yan.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrl;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrl.getWechatMpAuthorize()+"/sell/wechat/userinfo";
        String redirecturl=wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.error("微信访问获取code的url={}",url);
        log.error("state参数={}",returnUrl);
        log.error("redirecturl={}",redirecturl);
        //配置
        //调用方法
        return "redirect:"+redirecturl;

    }

    @GetMapping("/userinfo")
    public String test(@RequestParam("code") String code,@RequestParam("state") String state){
        log.error("state参数={}",state);
        log.error("获取的code={}",code);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String Token=wxMpOAuth2AccessToken.getAccessToken();
        System.out.println("Token:"+Token);
        String openId=wxMpOAuth2AccessToken.getOpenId();
        System.out.println(openId);
        return "redirect:"+state+"?openid="+openId;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        System.out.println(returnUrl);
        return "redirect:"+(projectUrl.getWechatMpAuthorize()+"/sell/wechat/qrUserInfo");
    }
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(){
        String state="http://v5gmgr.s1.natapp.cc";
        return "redirect:"+state+"/sell/seller/login?openid=1";
    }
}
