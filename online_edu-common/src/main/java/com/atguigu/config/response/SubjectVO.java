package com.atguigu.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/7/2
 * Author:zh
 * Description:发给前端的进行数据封装的类
 */
@Data
public class SubjectVO {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "课程分类名称")
    private String title;
    @ApiModelProperty(value = "课程分类子集合")

    private List<SubjectVO> children=new ArrayList<>();
    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "冗余字段")
    private String videoOriginalName;
}

