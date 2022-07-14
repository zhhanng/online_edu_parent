package com.atguigu.controller;


import com.atguigu.config.response.ChapterVO;
import com.atguigu.config.response.RetVal;
import com.atguigu.entity.EduChapter;
import com.atguigu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    /**
     * 查询所有章节和小节的方法,小节被封装在章节中,返回章节
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterAndSection/{chapterId}")
    public RetVal getChapterAndSection(@PathVariable String chapterId){
        List<ChapterVO> chapterVOList = chapterService.getChapterVOList(chapterId);
        return RetVal.success().data("chapterList",chapterVOList);
    }

    @PostMapping("saveChapter")
    public RetVal saveChapter(EduChapter chapter){
        boolean flag=chapterService.saveChapter(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    //3.根据章节id查询章节信息
    @GetMapping("getChapter/{id}")
    public RetVal getChapter(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return RetVal.success().data("chapter",chapter);
    }
    //4.更新章节信息
    @PutMapping("/updateChapter")
    public RetVal updateChapter(EduChapter chapter){
        boolean flag = chapterService.updateById(chapter);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    //5.删除章节信息
    @DeleteMapping("{chapterId}")
    public RetVal deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
}

