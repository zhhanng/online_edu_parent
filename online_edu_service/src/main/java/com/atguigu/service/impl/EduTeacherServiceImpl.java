package com.atguigu.service.impl;

import com.atguigu.config.request.TeacherConditionVO;
import com.atguigu.entity.EduTeacher;
import com.atguigu.mapper.EduTeacherMapper;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2022-06-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Override
    public void getTeachersByCondition(Page<EduTeacher> page, TeacherConditionVO teacherConditionVO) {
        if (teacherConditionVO == null) {
            teacherConditionVO = new TeacherConditionVO();
        }
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        String name = teacherConditionVO.getName();
        Integer level = teacherConditionVO.getLevel();
        String beginTime = teacherConditionVO.getBeginTime();
        String endTime = teacherConditionVO.getEndTime();
        if (!StringUtils.isEmpty(name)) {
            eduTeacherQueryWrapper.like("name", name);
        }
        if (level != null) {
            eduTeacherQueryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(beginTime)){
            eduTeacherQueryWrapper.ge("gmt_create",beginTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            eduTeacherQueryWrapper.le("gmt_create",endTime);
        }
        eduTeacherQueryWrapper.orderByDesc("gmt_create");
       baseMapper.selectPage(page, eduTeacherQueryWrapper);
    }
    //后端方法
///////////////////////////////////////////////////////////////////////////////////
    //前端方法

    /**
     * 前端查询教师列表和详情的方法
     * 返回分页的各个信息供前端使用
     * @param pageNum :页数
     * @param pageSize:分页大小
     * @return
     */
    @Override
    public Map<String, Object> getTeacherListPage(Long pageNum, Long pageSize) {
        Page<EduTeacher> tPage = new Page<EduTeacher>(pageNum, pageSize);
        //分页查询,会把数据封装在传入的Page中,不需要再接收
        baseMapper.selectPage(tPage, null);
        long total = tPage.getTotal();
        long totalPages = tPage.getPages();
        List<EduTeacher> teacherList = tPage.getRecords();
        long currentPage = tPage.getCurrent();
        long size = tPage.getSize();
        boolean hasPrevious = tPage.hasPrevious();
        boolean hasNext = tPage.hasNext();
        HashMap<String, Object> retVal = new HashMap<>();
        retVal.put("teacherList", teacherList);
        retVal.put("currentPage", currentPage);
        retVal.put("Pages", totalPages);
        retVal.put("size", size);
        retVal.put("total", total);
        retVal.put("hasNext", hasNext);
        retVal.put("hasPrevious", hasPrevious);
        return retVal;
    }
}
