package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Date:2022/6/28
 * Author:zh
 * Description:
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.atguigu.mapper")
public class EduTeactureApplication {
    public static void main(String[] args) {
        //TODO
        SpringApplication.run(EduTeactureApplication.class,args);
    }


}
