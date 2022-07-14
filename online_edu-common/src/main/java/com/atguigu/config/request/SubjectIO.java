package com.atguigu.config.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Date:2022/7/3
 * Author:zh
 * Description:将所传来数据进封装的类,新增科目
 */
@Data
public class SubjectIO {
    @ApiModelProperty(value = "科目名称")
    private String title;
    @ApiModelProperty(value = "一级科目iD")
    private String parentId;
}
