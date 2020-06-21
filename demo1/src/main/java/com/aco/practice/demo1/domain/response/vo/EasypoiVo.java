package com.aco.practice.demo1.domain.response.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: HaoJianXu
 * @Date: 2020/6/21 10:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasypoiVo implements Serializable {
    private String column;

    private String key;
}
