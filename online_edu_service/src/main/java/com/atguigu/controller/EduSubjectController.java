package com.atguigu.controller;


import com.atguigu.config.request.SubjectIO;
import com.atguigu.config.response.RetVal;
import com.atguigu.config.response.SubjectVO;
import com.atguigu.entity.EduSubject;
import com.atguigu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zh
 * @since 2022-07-02
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 文件上传的接口方法
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public RetVal uploadFile(MultipartFile file) throws IOException {
        eduSubjectService.uploadFile(file);
        return RetVal.success();
    }

    /**
     *删除科目的接口方法
     * @param id
     * @return
     */
    @DeleteMapping("/deleteSubject/{id}")
    public RetVal deleteSubject(@PathVariable String id){
        boolean flag = eduSubjectService.deleteParentSubject(id);
      if(flag){
          return RetVal.success();
      }else {
          return RetVal.error();
      }
    }

    /**
     * 查询所有科目,返回的是一个具有层次结构的集合
     * @return
     */
    @GetMapping("/getAllSubjects")
    public RetVal getAllSubject(){
        List<SubjectVO> allEduSubjects = eduSubjectService.getAllEduSubjects();
        return RetVal.success().data("subjectVO",allEduSubjects);
    }

    /**
     * 保存一级目录结构的接口方法
     * @param subjectIo
     * @return
     */
    @PostMapping("/saveParentSubject")
    public RetVal saveParentSubject(SubjectIO subjectIo){
        EduSubject eduSubject = new EduSubject();
        eduSubject.setTitle(subjectIo.getTitle());
        eduSubject.setParentId(subjectIo.getParentId());
        boolean flag = eduSubjectService.saveParentSubject(eduSubject);
        if(flag){
           return RetVal.success();
        }else {
           return RetVal.error();
        }
    }


    /**
     * 保存二级目录结构的接口方法
     * @param subjectIo
     * @return
     */
    @PostMapping("/saveChildSubject")
    public RetVal saveChildrenSubject(SubjectIO subjectIo){
        EduSubject eduSubject = new EduSubject();
        eduSubject.setTitle(subjectIo.getTitle());
        eduSubject.setParentId(subjectIo.getParentId());
        boolean flag = eduSubjectService.saveChildSubject(eduSubject);
        if(flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
}

