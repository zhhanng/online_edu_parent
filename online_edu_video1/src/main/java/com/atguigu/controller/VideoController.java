package com.atguigu.controller;

import com.atguigu.config.response.RetVal;
import com.atguigu.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/aliyun")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;
    //1.视频上传
    @PostMapping("uploadAliyunVideo")
    public RetVal uploadAliyunVideo(MultipartFile file) throws Exception{
        String videoId=videoService.uploadAliyunVideo(file);
        return RetVal.success().data("videoId",videoId);
    }
    //2.删除单个视频
    @DeleteMapping("deleteSingleVideo/{videoId}")
    public RetVal deleteSingleVideo(@PathVariable String videoId) throws Exception{
        boolean flag=videoService.deleteSingleVideo(videoId);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    //3.删除多个视频  RequestParam将请求参数区数据映射到功能处理方法的参数上
    @DeleteMapping("deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList) throws Exception{
        //只需要把list转换为String 以逗号隔开
        String videoIdListString=StringUtils.join(videoIdList, ",");
        boolean flag=videoService.deleteSingleVideo(videoIdListString);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String join = StringUtils.join(list, ",");
        System.out.println(join);
    }

    /**
     * 查询播放视频的凭证
     * @param videoId
     * @return
     */
    @GetMapping("getVideoPlayAuth/{videoId}")
    public RetVal getVideoPlayAuth(@PathVariable String videoId){
        String playAuth=videoService.getVideoPlayAuth(videoId);
        return RetVal.success().data("playAuth",playAuth);
    }
}
