package com.wechat.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 手工方式获取code和userInfo
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        log.info( "进入auth方法。。。" );
        log.info( "code={}", code );

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7393c10fc7289616&secret=fcbdfe741ba9b23004485d1156781ea7&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject( url, String.class );
        log.info( "response={}", response );
    }
}
