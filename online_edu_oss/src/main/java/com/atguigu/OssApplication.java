package com.atguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Date:2022/7/1
 * Author:zh
 * Description:
 */
@SpringBootApplication
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();

        c.setIgnoreUnresolvablePlaceholders(true);

        return c;
    }

}
