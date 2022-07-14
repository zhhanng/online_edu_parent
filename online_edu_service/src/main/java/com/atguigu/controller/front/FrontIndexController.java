package com.atguigu.controller.front;


import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduBanner;
import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduTeacher;
import com.atguigu.service.EduBannerService;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/edu/front")
@CrossOrigin
public class FrontIndexController {
    @Autowired
    private EduBannerService eduBannerService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 首页banner
     *
     * @return
     */
    @GetMapping("getAllBanner")
    public RetVal getAllBanners() {
        List<EduBanner> eduBannerList = eduBannerService.list(null);
        return RetVal.success().data("eduBannerList", eduBannerList);
    }

    @GetMapping("queryCourseAndTeacher")
    //热门课程和热门讲师
    public RetVal getHotCourseAndTeacher() {
        //查询热门课程,拿八个
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourselist = eduCourseService.list(courseQueryWrapper);
        //查询大咖讲师,按sort查询,拿四个
        QueryWrapper<EduTeacher> teacherqueryWrapper = new QueryWrapper<>();
        teacherqueryWrapper.orderByDesc("sort");
        teacherqueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherlist = eduTeacherService.list(teacherqueryWrapper);
        return RetVal.success().data("courseList",eduCourselist).data("teacherList",eduTeacherlist);
    }


}

