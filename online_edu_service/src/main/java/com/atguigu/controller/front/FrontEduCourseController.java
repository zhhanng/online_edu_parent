package com.atguigu.controller.front;


import com.atguigu.config.response.ChapterVO;
import com.atguigu.config.response.CourseDetailInfo;
import com.atguigu.config.response.RetVal;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 前端课程类,两个方法,一个查询课程列表,一个查询课程详情
 *
 * @author zh
 * @since 2022-07-09
 */
@RestController
@RequestMapping("/front/edu/course")
@CrossOrigin
public class FrontEduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    /**
     * 前端所要的查询课程列表方法,需要返回各种封面信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/{pageNum}/{pageSize}")
    public RetVal getTeacherListPage(@PathVariable Long pageNum,
                                     @PathVariable Long pageSize) {
        Map<String, Object> retMap = eduCourseService.getTeacherListPage(pageNum, pageSize);
        return RetVal.success().data(retMap);
    }

    /**
     * 前端课程详情接口,需要自定义sql语句和自定义数据封装类,
     * chapter和section的层次查询后端已经做好了,可以直接使用,
     * 也需要自定义SQL查询
     *
     * @param courseId
     * @return
     */
    @GetMapping("getCourseDetailsByCourseId/{courseId}")
    public RetVal getCourseDetailsByCourseId(@PathVariable String courseId) {
        CourseDetailInfo courseDetailInfo = eduCourseService.getCourseDetails(courseId);
        List<ChapterVO> chapterVOList = eduChapterService.getChapterVOList(courseId);
        return RetVal.success().data("courseDetail", courseDetailInfo).data("chapterVOList",chapterVOList);
    }


}

