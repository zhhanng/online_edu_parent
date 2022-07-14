package com.atguigu.handler;

import com.atguigu.edu.service.VideoFeignService;
import com.atguigu.config.response.RetVal;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class VideoServiceHandlerImpl implements VideoFeignService {
    //降级方法
    @Override
    public RetVal deleteSingleVideo(String videoId) {
        //还有一大堆代码等着你写
        return RetVal.error().message("兜底数据");
    }

    @Override
    public RetVal deleteMultiVideo(List<String> videoIdList) {
        //还有一大堆代码等着你写
        return RetVal.error().message("兜底数据");
    }
}
