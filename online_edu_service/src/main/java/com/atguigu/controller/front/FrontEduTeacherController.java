package com.atguigu.controller.front;


import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduTeacher;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu/front/teacher")
@CrossOrigin
public class FrontEduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    EduCourseService eduCourseService;

    @Autowired
    EduChapterService eduChapterService;

    /**
     * 查询教师列表,不需要额外附加条件
     *
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public RetVal getTeacherListPage(@PathVariable Long pageNum,
                                     @PathVariable Long pageSize) {
        Map<String, Object> retMap = eduTeacherService.getTeacherListPage(pageNum, pageSize);
        return RetVal.success().data(retMap);
    }

    @GetMapping("queryTeacherDetailById/{teacherId}")
    public RetVal getTeacherDetailsById(@PathVariable String teacherId) {
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);
        return RetVal.success().data("teacher", eduTeacher).data("courseList", eduCourseList);
    }


}

