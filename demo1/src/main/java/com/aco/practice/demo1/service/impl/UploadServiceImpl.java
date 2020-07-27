package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.domain.entity.FileEntity;
import com.aco.practice.demo1.domain.request.dto.FileDto;
import com.aco.practice.demo1.mapper.FileMapper;
import com.aco.practice.demo1.service.UploadService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UploadServiceImpl extends ServiceImpl<FileMapper,FileEntity> implements UploadService
{
    @Override
    public String UploadFile(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity();
        BeanUtils.copyProperties(fileDto,fileEntity);
        this.baseMapper.insert(fileEntity);
        log.info("文件信息：{}", JSONObject.toJSONString(fileEntity));
        return null;
    }
}
