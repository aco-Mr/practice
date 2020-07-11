package com.aco.practice.demo1.domain.entity;

import java.util.Date;

import com.aco.practice.demo1.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: HaoJianXu
* @Date: 2020/7/11 20:29
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class FileEntity extends BaseEntity {

    /**
    * 用户id
    */
    private String userId;

    /**
    * 文件名
    */
    private String fileName;

    /**
    * 文件存放地址
    */
    private String fileUrl;

    /**
    * 类型
    */
    private String fileType;

    /**
    * 状态：0为正常，1为删除
    */
    private Integer state;

}