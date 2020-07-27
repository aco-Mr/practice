package com.aco.practice.demo1.domain.request.dto;

import lombok.Data;

@Data
public class FileDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件存放地址
     */
    private String fileUrl;

    /**
     * 类型
     */
    private String fileType;
}
