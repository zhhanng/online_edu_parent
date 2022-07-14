package com.atguigu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.config.MyException;
import com.atguigu.config.response.SubjectVO;
import com.atguigu.entity.EduSubject;
import com.atguigu.entity.SubjectExcel;
import com.atguigu.listener.MyReadExcelListner;
import com.atguigu.mapper.EduSubjectMapper;
import com.atguigu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zh
 * @since 2022-07-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Autowired
    private MyReadExcelListner myReadExcelListner;


    /**
     * 查询数据库中所有的subject对象,为什么要写在service中?
     * 因为controller返回的是SubjectVO,相关的数据封装转换在service层完成
     *
     * @return :返回的是一个SubjectVO集合,而且自带层级结构
     */
    @Override
    public List<SubjectVO> getAllEduSubjects() {
        //先拿到所有的科目,一级和二级
        List<EduSubject> eduSubjects = baseMapper.selectList(null);
        //创建一个集合将要返回的SubjectVo放进去,具体的数据封装转换在迭代的时候进行
        ArrayList<SubjectVO> eduSubjectArrayList = new ArrayList<>();
        //迭代集合拿到一级科目,将数据转换到subjectVO上后,放入map集合中
        HashMap<String, SubjectVO> parentHashMap = new HashMap<>();
        for (EduSubject eduSubject : eduSubjects) {
            /*
            找到父科目,放入Map中,为什么这样,返回给前端的数据是一个层级结构,将子节点作为集合属性付给父节点
            map集合好找父节点
             */
            if ("0".equals(eduSubject.getParentId())) {
                SubjectVO subjectVO = new SubjectVO();
                subjectVO.setTitle(eduSubject.getTitle());
                subjectVO.setId(eduSubject.getId());
                parentHashMap.put(subjectVO.getId(),subjectVO);
                eduSubjectArrayList.add(subjectVO);
            }
        }
        //迭代找二级科目,放在其父节点的属性集合中
        for (EduSubject eduSubject : eduSubjects) {
            /*
            找到父科目,放入Map中,为什么这样,返回给前端的数据是一个层级结构,将子节点作为集合属性付给父节点
            map集合好找父节点
             */
            if (!"0".equals(eduSubject.getParentId())) {
                SubjectVO subjectVO1 = parentHashMap.get(eduSubject.getParentId());
                SubjectVO subjectVO = new SubjectVO();
                subjectVO.setId(eduSubject.getId());
                subjectVO.setTitle(eduSubject.getTitle());
                //把这个subjectvo放到其父节点的集合中
                subjectVO1.getChildren().add(subjectVO);
            }
        }
        //返回一级目录,返回的是一个层级结构
        return eduSubjectArrayList;
    }


    /**
     * 文件上传接口,监听器中invoke方法会将文件中的科目写入数据库
     *
     * @param file
     * @throws IOException
     */
    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream, SubjectExcel.class, myReadExcelListner).doReadAll();
    }


    /**
     * 用来判断某一个科目是否已经存在,在新插入一级和二级课程时都需要进行判断
     *
     * @param parentId    : 父节点的ID,不同父节点下的同名课程算不同课程
     * @param subjectNmae :要判断的课程名称
     * @return :返回一个对象就行,具体的业务逻辑由所调用的方法进行书写
     */
    @Override
    public EduSubject exitsSubject(String parentId, String subjectNmae) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("title", subjectNmae);
        //先用条件进行查询,返回值为null表明存在,否则不存在
        return baseMapper.selectOne(wrapper);
    }


    /**
     * 插入保存一级目录的方法
     *
     * @param eduSubject:前端传过来封装的对象
     * @return 如果保存成功返回true, 否则产生自定义异常
     */
    @Override
    public boolean saveParentSubject(EduSubject eduSubject) {
        EduSubject parentSubject = exitsSubject("0", eduSubject.getTitle());
        if (parentSubject == null) {
            eduSubject.setParentId("0");
            baseMapper.insert(eduSubject);
            return true;
        }
        throw new MyException(20001, "该一级科目已存在");
    }


    /**
     * 插入保存一级目录的方法
     *
     * @param eduSubject:前端传过来封装的对象
     * @return 如果保存成功返回true, 否则产生自定义异常
     */
    @Override
    public boolean saveChildSubject(EduSubject eduSubject) {
        EduSubject childSubject = exitsSubject(eduSubject.getParentId(), eduSubject.getTitle());
        if (childSubject == null) {
            baseMapper.insert(eduSubject);
            return true;
        } else {
            throw new MyException(20001, "该二级科目已存在");
        }
    }

    /**
     * 删除方法,需要先判断该科目下还有没有二级科目
     * 有二级科目不能删,否则可以删除
     *
     * @param id:前端传入的科目的id
     * @return 是否成功删除
     */
    @Override
    public boolean deleteParentSubject(String id) {
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(eduSubjectQueryWrapper);
        if (count == 0) {
            //可以删
            baseMapper.deleteById(id);
            return true;
        } else {
            throw new MyException(20001, "不可删除非空一级科目");
        }
    }
}


