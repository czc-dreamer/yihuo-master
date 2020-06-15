package com.yihuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 98050
 * Time: 2018-08-09 14:12
 * Feature:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class YhUploadService {
    public static void main(String[] args) {
        SpringApplication.run(YhUploadService.class, args);
    }
}
