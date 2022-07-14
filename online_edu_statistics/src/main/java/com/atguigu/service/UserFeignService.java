package com.atguigu.service;


import com.atguigu.config.response.RetVal;
import com.atguigu.handler.UserServiceHandlerImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "EDU-USER",fallback = UserServiceHandlerImpl.class)
public interface UserFeignService {
    //远程调用查询方法
    @GetMapping("/member/center/queryRegistryNum/{day}")
    public RetVal getqueryRegistryNum(@PathVariable String day);
}
