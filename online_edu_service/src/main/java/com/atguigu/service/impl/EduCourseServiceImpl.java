package com.atguigu.service.impl;

import com.atguigu.config.request.CourseCondition;
import com.atguigu.config.request.CourseVO;
import com.atguigu.config.response.ChapterVO;
import com.atguigu.config.response.CourseDetailInfo;
import com.atguigu.config.response.EduCourseConfirmVo;
import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduCourseDescription;
import com.atguigu.entity.EduTeacher;
import com.atguigu.mapper.EduCourseMapper;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduCourseDescriptionService;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduSectionService eduSectionService;
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 多表插入,需要加事务
     *
     * @param coursevo :传入的信息数据
     * @return
     */
    @Override
    @Transactional
    public String saveCourse(CourseVO coursevo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(coursevo, eduCourse);
        //基础信息需要加在eduCourse中
        int row = baseMapper.insert(eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(coursevo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        //描述信息加在educoursedescription中,需要保证两张表的id一致
        Integer row1 = eduCourseDescriptionService.insert(eduCourseDescription);
        return eduCourse.getId();
    }


    /**
     * 多表更新,需要加事务
     *
     * @param coursevo:传入的信息数据
     * @return
     */
    @Override
    @Transactional
    public Boolean updateCourse(CourseVO coursevo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(coursevo, eduCourse);
        int row = baseMapper.updateById(eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(coursevo, eduCourseDescription);
        boolean flag = eduCourseDescriptionService.updateById(eduCourseDescription);
        return row > 0 && flag;
    }


    /**
     * 根据id查询课程信息的方法,需要查询两个表(course和description)
     * 两个表的信息合并到courseVO中
     * 教师表的id和讲师姓名对应在前端进行
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseVO getCourseVoById(String courseId) {
        CourseVO courseVO = new CourseVO();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourse, courseVO);
        if(eduCourseDescription!=null){
            courseVO.setDescription(eduCourseDescription.getDescription());
        }
        return courseVO;
    }

    /**
     * 分页查询
     * @param eduCoursePage
     * @param courseCondition
     */
    @Override
    public void queryCoursePageByCondition(Page<EduCourse> eduCoursePage, CourseCondition courseCondition) {
        String status = courseCondition.getStatus();
        String title = courseCondition.getTitle();
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status",status);
        }
        if(StringUtils.isNotEmpty(title)){
            queryWrapper.like("title",title);
        }
        baseMapper.selectPage(eduCoursePage,queryWrapper);
    }

    @Override
    public EduCourseConfirmVo getCourseConfirmInfo(String courseId) {
        return baseMapper.getCourseConfirmInfo(courseId);
    }

    @Override
    public void deleteCourseById(String courseId) {
        //a.该课程所对应的所有章节
       eduChapterService.deleteChapterByCourseId(courseId);
        //b.该课程所对应的所有小节
        eduSectionService.deleteSectionByCourseId(courseId);
        //c.该课程的基本信息
        baseMapper.deleteById(courseId);
        //c.该课程的描述信息
        eduCourseDescriptionService.removeById(courseId);
        //d.整个过程要保证完整性(分布式事务)
    }

    //后端部分
    ///////////////////////////////////////////////////////////////////
    //前端部分


    @Override
    public Map<String, Object> getTeacherListPage(Long pageNum, Long pageSize) {
        Page<EduCourse> tPage = new Page<EduCourse>(pageNum, pageSize);
        //分页查询,会把数据封装在传入的Page中,不需要再接收
        baseMapper.selectPage(tPage, null);
        long total = tPage.getTotal();
        long totalPages = tPage.getPages();
        List<EduCourse> teacherList = tPage.getRecords();
        long currentPage = tPage.getCurrent();
        long size = tPage.getSize();
        boolean hasPrevious = tPage.hasPrevious();
        boolean hasNext = tPage.hasNext();
        HashMap<String, Object> retVal = new HashMap<>();
        retVal.put("courseList", teacherList);
        retVal.put("currentPage", currentPage);
        retVal.put("Pages", totalPages);
        retVal.put("size", size);
        retVal.put("total", total);
        retVal.put("hasNext", hasNext);
        retVal.put("hasPrevious", hasPrevious);
        return retVal;
    }

    /**
     * 查询课程信息详情的方法
     * @param courseId
     * @return
     */
    @Override
    public CourseDetailInfo getCourseDetails(String courseId) {
       return baseMapper.getCourseDetailsById(courseId);
    }


}
