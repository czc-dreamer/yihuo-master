package com.yihuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 98050
 * Time: 2018-10-11 16:43
 * Feature: 启动器，开启fegin功能
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class YhSearchService {
    public static void main(String[] args) {
        SpringApplication.run(YhSearchService.class,args);
    }
}
