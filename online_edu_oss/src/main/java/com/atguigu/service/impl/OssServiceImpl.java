package com.atguigu.service.impl;

import com.atguigu.service.OssService;
import com.atguigu.template.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Date:2022/7/1
 * Author:zh
 * Description:
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private OssUtils ossUtils;

    @Override
    public boolean deleteFile(String filename) {

        ossUtils.deleteFile(filename);
        return true;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prefix = UUID.randomUUID().toString().replace("-", "");
        String changeFileName = prefix + suffix;
        return ossUtils.uploadFile(inputStream, changeFileName);
    }
}
