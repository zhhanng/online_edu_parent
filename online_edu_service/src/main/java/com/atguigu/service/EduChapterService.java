package com.atguigu.service;

import com.atguigu.config.response.ChapterVO;
import com.atguigu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-04
 */
public interface EduChapterService extends IService<EduChapter> {

    boolean deleteChapter(String chapterId);

    boolean saveChapter(EduChapter chapter);

    List<ChapterVO> getChapterVOList(String courseId);

    void deleteChapterByCourseId(String courseId);
}
