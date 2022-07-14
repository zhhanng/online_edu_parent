package com.atguigu.mapper;

import com.atguigu.config.response.CourseDetailInfo;
import com.atguigu.config.response.EduCourseConfirmVo;
import com.atguigu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    EduCourseConfirmVo getCourseConfirmInfo(String courseId);

    CourseDetailInfo getCourseDetailsById(String courseId);

}
