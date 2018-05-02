package com.yan.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {
    private String wechatMpAuthorize;

    private String sell;
}
