package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Date:2022/7/12
 * Author:zh
 * Description:
 */
@SpringBootApplication
@MapperScan("com.atguigu.mapper")
public class EduPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduPayApplication.class,args);
    }
}
