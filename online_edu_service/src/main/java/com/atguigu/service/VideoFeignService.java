package com.atguigu.edu.service;

import com.atguigu.handler.VideoServiceHandlerImpl;
import com.atguigu.config.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(value = "EDU-VIDEO",fallback = VideoServiceHandlerImpl.class)
public interface VideoFeignService {
    //2.删除单个视频
    @DeleteMapping("/aliyun/deleteSingleVideo/{videoId}")
    public RetVal deleteSingleVideo(@PathVariable String videoId);
    //3.删除多个视频  RequestParam将请求参数区数据映射到功能处理方法的参数上
    @DeleteMapping("/aliyun/deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
