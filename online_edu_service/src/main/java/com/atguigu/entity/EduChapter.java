package com.atguigu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_chapter")
@ApiModel(value="EduChapter对象", description="课程")
public class EduChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "章节ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill= FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;


}
