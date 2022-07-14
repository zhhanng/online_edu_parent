package com.atguigu.service.impl;

import com.atguigu.config.MyException;
import com.atguigu.config.response.ChapterVO;
import com.atguigu.entity.EduChapter;
import com.atguigu.entity.EduSection;
import com.atguigu.mapper.EduChapterMapper;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduSectionService eduSectionService;

    @Override
    public boolean saveChapter(EduChapter chapter) {
        //判断是否存在
        EduChapter existChapter = existChapter(chapter.getCourseId(), chapter.getTitle());
        if (existChapter == null) {
            int insert = baseMapper.insert(chapter);
            return insert > 0;
        } else {
            throw new MyException(20001, "章节已经重复");
        }
    }


    public EduChapter existChapter(String courseId, String chapterName) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("title", chapterName);
        EduChapter chapter = baseMapper.selectOne(queryWrapper);
        return chapter;

    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //判断是否有小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = eduSectionService.count(queryWrapper);
        //没有小节
        if (count == 0) {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        } else {
            throw new MyException(20001, "该章节存在小节");
        }
    }

    @Override
    public List<ChapterVO> getChapterVOList(String courseId) {
        QueryWrapper<EduChapter> chapterVOQueryWrapper = new QueryWrapper<>();
        chapterVOQueryWrapper.eq("course_id", courseId);
        chapterVOQueryWrapper.orderByAsc("sort");
        List<EduChapter> chapterList = baseMapper.selectList(chapterVOQueryWrapper);
        QueryWrapper<EduSection> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("course_id", courseId);
        sectionQueryWrapper.orderByAsc("sort");
        List<EduSection> sectionList = eduSectionService.list(sectionQueryWrapper);
        ArrayList<ChapterVO> chapterVOS = new ArrayList<>();
        //和一,二级科目一样,都遍历,符合条件的把section放入chapter中
        //先循环少的,再循环大的
        for (EduChapter eduChapter : chapterList) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            for (EduSection eduSection : sectionList) {
                ChapterVO sectionVO = new ChapterVO();
                BeanUtils.copyProperties(eduSection, sectionVO);
                if (eduSection.getChapterId().equals(eduChapter.getId())) {
                    chapterVO.getChildren().add(sectionVO);
                }
            }
            chapterVOS.add(chapterVO);
        }
        return chapterVOS;
    }
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
