package com.atguigu.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/7/5
 * Author:zh
 * Description:封装了章节和小节数据方法
 */
@Data
public class ChapterVO {
    @ApiModelProperty(value = "章节小节ID")
    private String id;

    @ApiModelProperty(value = "章节小节ID")
    private String title;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "冗余字段")
    private String videoOriginalName;

    private List<ChapterVO> children = new ArrayList<>();
}
