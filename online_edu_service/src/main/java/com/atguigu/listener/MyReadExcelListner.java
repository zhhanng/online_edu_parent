package com.atguigu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.entity.EduSubject;
import com.atguigu.entity.SubjectExcel;
import com.atguigu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Date:2022/7/2
 * Author:zh
 * Description:
 */
@Component
public class MyReadExcelListner extends AnalysisEventListener<SubjectExcel> {

    @Autowired
    private EduSubjectService eduSubjectService;


    /**
     * excel读完数据后进行的操作
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 一行一行读数据时可以进行的操作,需要将ExcelSubject中的数据封装到EduSubject中
     * 涉及到多个sql语句操作需要添加事务
     *
     * @param subjectExcel
     * @param analysisContext
     */
    @Transactional
    @Override
    public void invoke(SubjectExcel subjectExcel, AnalysisContext analysisContext) {
       //父节点和子节点插入数据库都要先进行判断
        String parentSubjectName = subjectExcel.getParentSubjectName();
        EduSubject eduSubject = eduSubjectService.exitsSubject("0",parentSubjectName);
        if (eduSubject==null) {
            //说明可以插入,新建对象进行插入
            eduSubject = new EduSubject();
            eduSubject.setTitle(parentSubjectName);
            eduSubject.setParentId("0");
            eduSubjectService.save(eduSubject);
        }
        String childSubjectName = subjectExcel.getChildSubjectName();
        //这里是一个要点,这里开启了事务,严格来说上面的父节点还没写入到数据库
        //为什么可以得到其插入后的id呢?因为mybatisplus的雪花算法,全局id唯一
        EduSubject eduSubject1 = eduSubjectService.exitsSubject(eduSubject.getId(),childSubjectName);
        if (eduSubject1==null) {
            eduSubject1 = new EduSubject();
            eduSubject1.setTitle(childSubjectName);
            eduSubject1.setParentId(eduSubject.getId());
            eduSubjectService.save(eduSubject1);
        }
    }
}
