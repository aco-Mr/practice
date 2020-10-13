package com.aco.practice.demo1.util;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;

/**
 * @Author: HaoJianXu
 * @Date: 2020/10/13 21:49
 */
public class VerificationCodeUtil {

    public static final int CAPTCHA_WIDTH = 120;

    public static final int CAPTCHA_HEIGHT = 40;

    /**
     * 获取算术验证码
      * @return
     */
    public static Captcha getArithmeticCaptcha(){
        //算术验证码 数字加减乘除. 建议2位运算就行:captcha.setLen(2);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        captcha.setLen(2);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        return captcha;
    }

    /**
     * 获取中文验证码
     * @return
     */
    public static String getChineseCaptcha(){
        //中文验证码
        ChineseCaptcha captcha = new ChineseCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        captcha.setLen(4);
        return captcha.text();
    }

    /**
     * 获取英文与数字验证码
     * @return
     */
    public static String getSpecCaptcha (){
        //英文与数字验证码
        SpecCaptcha captcha = new SpecCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        captcha.setLen(4);
        return captcha.text();
    }

    /**
     * 获取英文与数字动态验证码
     * @return
     */
    public static String getGifCaptcha (){
        //英文与数字动态验证码
        GifCaptcha captcha = new GifCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        captcha.setLen(4);
        return captcha.text();
    }

    /**
     * 获取中文动态验证码
     * @return
     */
    public static String getChineseGifCaptcha (){
        //中文动态验证码
        ChineseGifCaptcha captcha = new ChineseGifCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        captcha.setLen(4);
        return captcha.text();
    }

    /**
     * 获取自定义验证码
     * @return
     */
    public static String getCaptcha(){
        //中文动态验证码
        Captcha captcha = new GifCaptcha();
        captcha.setLen(4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        captcha.setWidth(CAPTCHA_WIDTH);
        captcha.setHeight(CAPTCHA_HEIGHT);
        return captcha.text();
    }
}
