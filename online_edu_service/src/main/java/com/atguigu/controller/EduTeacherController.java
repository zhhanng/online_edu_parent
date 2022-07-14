package com.atguigu.controller;


import com.atguigu.config.request.TeacherConditionVO;
import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduTeacher;
import com.atguigu.mapper.EduTeacherMapper;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    /**
     * 查询教师列表,不需要额外附加条件
     *
     * @return
     */
    @GetMapping
    public RetVal getEduTeacherList() {
        List<EduTeacher> eduTeacherlist = eduTeacherService.list(null);
        return RetVal.success().data("teachersList", eduTeacherlist);
    }

    /**
     * 逻辑删除指定的用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteTeacherById/{id}")
    public RetVal deleteTeacherById(@PathVariable String id) {
        eduTeacherService.removeById(id);
        return RetVal.success().message("删除成功");
    }

    /**
     * @param pageNum            当前页数
     * @param pageSize           页面大小
     * @param teacherConditionVO 搜索条件
     * @return
     */
    @GetMapping("/getTeachersByCondition/{pageNum}/{pageSize}")
    public RetVal getAllTeachersByCondition(@PathVariable Long pageNum, @PathVariable
            Long pageSize, TeacherConditionVO teacherConditionVO) {
        Page<EduTeacher> page = new Page<>(pageNum, pageSize);
        eduTeacherService.getTeachersByCondition(page, teacherConditionVO);
        //返回的是void.因为分页信息都被封装在传入的page中,拿到page中的total
        long total = page.getTotal();
        //拿到page中的List
        List<EduTeacher> teacherList = page.getRecords();
        return RetVal.success().data("teacherList",teacherList).data("total",total).message("查询成功");
    }

    /**
     * 添加讲师
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("/insertTeacher")
    public RetVal insertTeacher(EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return RetVal.success().message("添加成功");
        } else {
            return RetVal.error().message("添加失败");
        }
    }

    @PutMapping("/updateTeacher")
    public RetVal updateTeacher(EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return RetVal.success().message("修改成功");
        } else {
            return RetVal.error().message("修改失败");
        }
    }

    @GetMapping("/getTeacherById/{id}")
    public RetVal getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return RetVal.success().message("查询成功").data("eduTeacher",eduTeacher);
    }

}

