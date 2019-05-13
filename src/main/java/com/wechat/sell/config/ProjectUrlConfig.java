package com.wechat.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {
    /**
     * 微信公众平台
     */
    public String wechatMpAuthorize;

    /**
     * 微信开发平台
     */
    public String wechatOpenAuthorize;

    /**
     * 项目
     */
    public String sell;
}
