package com.atguigu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Date:2022/7/1
 * Author:zh
 * Description:
 */
public interface OssService {

    String uploadFile(MultipartFile file) throws IOException;

    boolean deleteFile(String filename);
}
