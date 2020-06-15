package com.yihuo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: 98050
 * @Time: 2018-11-28 16:39
 * @Feature: 配置中心
 */
@EnableConfigServer
@SpringBootApplication
public class YhConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhConfigApplication.class,args);
    }
}
