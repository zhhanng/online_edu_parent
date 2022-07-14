package com.atguigu.controller;


import com.atguigu.config.request.CourseCondition;
import com.atguigu.config.request.CourseVO;
import com.atguigu.config.response.EduCourseConfirmVo;
import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduCourse;
import com.atguigu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;


    /**
     * 课程发布接口
     *
     * @param courseId
     * @return
     */
    @GetMapping("publishCourse/{courseId}")
    public RetVal publishCourse(@PathVariable String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return RetVal.success();
    }

    /**
     * 课程确认的接口
     *
     * @param courseId
     * @return
     */
    @GetMapping("getCourseConfirmInfo/{courseId}")
    public RetVal getCourseConfirmInfo(@PathVariable String courseId) {
        EduCourseConfirmVo courseConfirmVo = eduCourseService.getCourseConfirmInfo(courseId);
        return RetVal.success().data("courseConfirm", courseConfirmVo);
    }

    /**
     * 新增课程信息
     *
     * @param coursevo:封装的后课程数据(包括description)
     * @return
     */
    @PostMapping("/insertCourse")
    public RetVal insertCourse(CourseVO coursevo) {
        String courseId = eduCourseService.saveCourse(coursevo);
        return RetVal.success().data("courseId", courseId);
    }

    /**
     * 保存课程信息
     *
     * @param coursevo:封装的后课程数据(包括description)
     * @return
     */
    @PostMapping("/saveCourse")
    public RetVal saveCourse(CourseVO coursevo) {
        Boolean flag = eduCourseService.updateCourse(coursevo);
        if (flag) {
            return RetVal.success();
        } else {
            return RetVal.error();
        }
    }

    /**
     * 通过id查询课程信息,修改回显需要使用
     *
     * @param courseId
     * @return
     */
    @GetMapping("getCourse/{courseId}")
    public RetVal getCourseById(@PathVariable String courseId) {
        CourseVO coursevo = eduCourseService.getCourseVoById(courseId);
        return RetVal.success().data("courseInfo", coursevo);
    }

    /**
     * 分页条件查询
     *
     * @return
     */
    @GetMapping("queryCoursePageByCondition/{pageNum}/{pageSize}")
    public RetVal queryCoursePageByCondition(@PathVariable("pageNum") Long pageNum,
                                             @PathVariable("pageSize") Long pageSize,
                                             CourseCondition courseCondition) {
        Page<EduCourse> eduCoursePage = new Page<EduCourse>(pageNum, pageSize);
        //分页查询,调用service层
        eduCourseService.queryCoursePageByCondition(eduCoursePage, courseCondition);
        List<EduCourse> courseList = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        return RetVal.success().data("courseList", courseList).data("total", total);
    }

    @DeleteMapping("deleteCourseById/{courseId}")
    public RetVal deleteCourseById(@PathVariable String courseId){
        eduCourseService.deleteCourseById(courseId);
        return RetVal.success();
    }
}

