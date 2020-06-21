package com.aco.practice.demo1.domain.response.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HaoJianXu
 * @Date: 2020/6/20 08:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportTestVo implements Serializable {

    private String name;

    private List<String> list;

}
