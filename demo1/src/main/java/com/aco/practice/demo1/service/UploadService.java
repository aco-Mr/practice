package com.aco.practice.demo1.service;

import com.aco.practice.demo1.domain.request.dto.FileDto;


public interface UploadService {
    /**
     * 上传文件
     * @param fileDto
     * @return
     */
    String UploadFile(FileDto fileDto);
}
