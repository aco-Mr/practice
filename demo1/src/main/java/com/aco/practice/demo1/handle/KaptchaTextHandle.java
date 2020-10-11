package com.aco.practice.demo1.handle;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

/**
 * 验证码文本生成器
 * @Author: HaoJianXu
 * @Date: 2020/10/11 22:06
 */
public class KaptchaTextHandle extends DefaultTextCreator {
    @Override
    public String getText() {
        return String.valueOf(Math.round(Math.random() * 10000));
    }
}
