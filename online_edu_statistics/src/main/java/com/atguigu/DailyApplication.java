package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Date:2022/7/8
 * Author:zh
 * Description:
 */
@SpringBootApplication
@MapperScan("com.atguigu.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class DailyApplication {
    public static void main(String[] args) {
        SpringApplication.run(DailyApplication.class,args);
    }
}
