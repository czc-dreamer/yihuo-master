package com.yihuo.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 98050
 * @Time: 2018-10-24 20:46
 * @Feature:购物车启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class YhCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCartApplication.class,args);
    }
}
