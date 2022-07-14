package com.atguigu.service;

import com.atguigu.config.request.CourseCondition;
import com.atguigu.config.request.CourseVO;
import com.atguigu.config.response.CourseDetailInfo;
import com.atguigu.config.response.EduCourseConfirmVo;
import com.atguigu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseVO coursevo);

    Boolean updateCourse(CourseVO coursevo);

    CourseVO getCourseVoById(String courseId);

    void queryCoursePageByCondition(Page<EduCourse> eduCoursePage, CourseCondition courseCondition);

    EduCourseConfirmVo getCourseConfirmInfo(String courseId);

    void deleteCourseById(String courseId);

    Map<String, Object> getTeacherListPage(Long pageNum, Long pageSize);

    CourseDetailInfo getCourseDetails(String courseId);
}
