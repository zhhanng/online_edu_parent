package com.atguigu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Date:2022/7/2
 * Author:zh
 * Description:封装导入excel数据的第一列和第二列,只需要给出get和set方法就行
 */
@Data
public class SubjectExcel {
    @ExcelProperty(value = "一级分类")
    private String parentSubjectName;
    @ExcelProperty(value = "二级分类")
    private String childSubjectName;
}
