package com.atguigu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Date:2022/6/29
 * Author:zh
 * Description:配置类,实现逻辑删除
 */
@Configuration
public class Myconfig {
    /**
     * 乐观锁插件,配合@version使用
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    /**
     * 逻辑删除插件,配合@TableLogic使用
     * @return
     */
    @Bean
   public ISqlInjector injector(){
        return new LogicSqlInjector() ;
    }

    /**
     * 配置分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
