package com.yihuo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 98050
 * @Time: 2018-10-24 16:55
 * @Feature: 过滤白名单
 */
//@ConfigurationProperties(prefix = "yihuo.filter")
@Configuration
@RefreshScope
public class FilterProperties {

    @Value("${yihuo.filter.allowPaths}")
    private String allowPaths;

    public String getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(String allowPaths) {
        this.allowPaths = allowPaths;
    }
}
