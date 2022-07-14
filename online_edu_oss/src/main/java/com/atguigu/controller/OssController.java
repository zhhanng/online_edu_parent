package com.atguigu.controller;

import com.atguigu.config.response.RetVal;
import com.atguigu.service.OssService;
import com.atguigu.template.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Date:2022/7/1
 * Author:zh
 * Description:
 */
@CrossOrigin
@RestController
@RequestMapping("/oss/")
public class OssController {

    @Autowired
    private OssUtils ossUtils;

    @Autowired
    private OssService ossService;

    /**
     * controller的上传文件方法,返回一个网址,这个网址会封装到数据库中
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("uploadFile")
    public RetVal uploadFile(MultipartFile file) throws IOException {
        String retUrl = ossService.uploadFile(file);
        return RetVal.success().data("retUrl", retUrl);
    }

    @DeleteMapping("deleteFile")
    public RetVal deleteFile(String fileName) {
        boolean flag = ossService.deleteFile(fileName);
        if (flag) {
            return RetVal.success();
        } else {
            return RetVal.error();
        }
    }
}
