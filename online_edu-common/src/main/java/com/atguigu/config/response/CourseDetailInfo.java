package com.atguigu.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 该类是封装了五个表的前端所要数据,前端查询课程详情时
 * 需要,教师信息,金额,课时数,科目分类,课程描述信息以及章小节等,这些数据
 * 封装在这个类中,需要写出对应的SQL语句
 */
import java.math.BigDecimal;
@Data
public class CourseDetailInfo {
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "一级分类id")
    private String parentSubjectId;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "二级分类ID")
    private String childSubjectId;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String intro;

    @ApiModelProperty(value = "讲师简介")
    private String career;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "一级分类")
    private String parentSubject;

    @ApiModelProperty(value = "二级分类")
    private String childSubject;
}
