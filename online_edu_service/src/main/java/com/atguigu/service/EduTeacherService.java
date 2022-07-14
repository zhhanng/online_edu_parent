package com.atguigu.service;

import com.atguigu.config.request.TeacherConditionVO;
import com.atguigu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2022-06-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void getTeachersByCondition(Page<EduTeacher> page, TeacherConditionVO teacherConditionVO);

    Map<String, Object> getTeacherListPage(Long pageNum, Long pageSize);
}
