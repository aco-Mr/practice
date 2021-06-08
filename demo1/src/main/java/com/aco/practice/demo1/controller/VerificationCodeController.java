package com.aco.practice.demo1.controller;

import com.aco.practice.demo1.constant.VerificationCodeConst;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.demo1.holder.UserContextHolder;
import com.aco.practice.demo1.util.VerificationCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @Author: HaoJianXu
 * @Date: 2020/10/11 21:19
 */
@RestController
@Slf4j
@Api(tags = "验证码控制器")
public class VerificationCodeController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping(value = "/code/create")
    @ApiOperation(value = "获取验证码")
    public void createCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中去
//            String createText = defaultKaptcha.createText();
            String createText = VerificationCodeUtil.getCaptcha();
            UserContextHolder<UserEntity> instance = UserContextHolder.getInstance();
            UserEntity user = instance.getUseParam("user");
            //给定一个唯一标识，防止验证码重复使用
            httpServletRequest.getSession().setAttribute(VerificationCodeConst.KAPTCHA_SESSION_KEY + user.getId(), createText);
            //redisCache.set(RedisCache.prefix, coldFlag, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();

    }

    @PostMapping(value = "/code/check")
    @ApiOperation(value = "校验验证码")
    public ResponseEntity checkVerificationCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody JSONObject data){
        //获取用户信息
        UserContextHolder<UserEntity> instance = UserContextHolder.getInstance();
        UserEntity user = instance.getUseParam("user");
        //获取验证码标识
        String kaptchaId = String.valueOf(httpServletRequest.getSession().getAttribute(VerificationCodeConst.KAPTCHA_SESSION_KEY + user.getId()));
        //前端验证码
        String code = data.getString("code");
        log.info("Session  verify  code：{}，from code：{}",kaptchaId,code);
        if (StringUtils.equals(kaptchaId,code)){
            return ResponseEntity.ok().body("验证成功");
        } else {
            throw new CustomException("验证码错误，请重新输入");
        }
    }
}
