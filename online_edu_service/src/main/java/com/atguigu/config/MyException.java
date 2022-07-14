package com.atguigu.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date:2022/7/2
 * Author:zh
 * Description:自定义异常类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException{
    @ApiModelProperty(value = "异常code")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String message;
}
