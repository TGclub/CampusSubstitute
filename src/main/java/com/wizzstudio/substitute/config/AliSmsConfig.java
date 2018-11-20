package com.wizzstudio.substitute.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云有关配置，主要为短信发送
 * Created By Cx On 2018/11/13 0:04
 */
@Component
@Data
@ConfigurationProperties("alibaba")
public class AliSmsConfig {

    private String accessKeyId;

    private String accessKeySecret;
}
