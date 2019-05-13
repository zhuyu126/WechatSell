package com.wechat.sell.controller;

import com.wechat.sell.config.ProjectUrlConfig;
import com.wechat.sell.constant.CookieConstant;
import com.wechat.sell.constant.RedisConstant;
import com.wechat.sell.enity.SellerInfo;
import com.wechat.sell.enums.ResultEnum;
import com.wechat.sell.service.SellerService;
import com.wechat.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 卖家用户功能
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1、openid和数据库里数据进行匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo==null){
            map.put("msg",ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2、设置token至redis
        String token= UUID.randomUUID().toString();
        //过期时间
        Integer expire= RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //3、设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        System.out.println(projectUrlConfig.sell);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object>map){
        //1、从cookie中查询
        Cookie cookie = CookieUtil.get( request, CookieConstant.TOKEN );
        if(cookie!=null){
            //2、清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3、清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
