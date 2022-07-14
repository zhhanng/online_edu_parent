package com.atguigu.service;

import com.atguigu.config.response.SubjectVO;
import com.atguigu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zh
 * @since 2022-07-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<SubjectVO> getAllEduSubjects();

    void uploadFile(MultipartFile file) throws IOException;

    EduSubject exitsSubject(String parentId, String subjectNmae);

    boolean saveParentSubject(EduSubject eduSubject);

    boolean saveChildSubject(EduSubject eduSubject);

    boolean deleteParentSubject(String id);
}
