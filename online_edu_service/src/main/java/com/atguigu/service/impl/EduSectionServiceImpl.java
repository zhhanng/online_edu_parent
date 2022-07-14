package com.atguigu.service.impl;

import com.atguigu.config.MyException;
import com.atguigu.entity.EduSection;
import com.atguigu.mapper.EduSectionMapper;
import com.atguigu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {

    @Autowired
    private com.atguigu.edu.service.VideoFeignService videoFeignService;


    @Override
    public void addSection(EduSection section) {
        //1.判断是否存在小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",section.getCourseId());
        queryWrapper.eq("chapter_id",section.getChapterId());
        queryWrapper.eq("title",section.getTitle());
        EduSection existSection = baseMapper.selectOne(queryWrapper);
        if(existSection==null){
            baseMapper.insert(section);
        }else{
            throw new MyException(20001,"存在重复的小节");
        }
    }


    @Override
    public void deleteSection(String id) {
        //根据小节id查询到小节视频id
        EduSection section = baseMapper.selectById(id);
        String videoSourceId = section.getVideoSourceId();
        if(StringUtils.isNotEmpty(videoSourceId)){
            //远程调用RPC 通过feign删除视频
            videoFeignService.deleteSingleVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteSectionByCourseId(String courseId) {
        //通过课程id找到该课程的所有小节
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduSection> sectionList = baseMapper.selectList(wrapper);
        //迭代所有的小节 把视频id封装到一个list里面
        List<String> videoIdList = new ArrayList<>();
        for (EduSection section : sectionList) {
            String videoSourceId = section.getVideoSourceId();
            if(StringUtils.isNotEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        //远程调用video微服务 删除多个视频的方法
        videoFeignService.deleteMultiVideo(videoIdList);
        //最后删除小节信息
        baseMapper.delete(wrapper);
    }
}
