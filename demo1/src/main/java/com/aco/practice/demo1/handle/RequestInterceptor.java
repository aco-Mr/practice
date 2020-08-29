package com.aco.practice.demo1.handle;

import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.demo1.util.RedisKeyUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author XHaoJian
 * @data 2020/6/14 21:50
 */
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final String[] URL_PATH = {"/aco/error","/aco/csrf","/aco/","/aco"};

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /** 之前 **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String pre = RedisKeyUtil.getRequestKey(requestUri);
        log.info("请求路径：{}",requestUri);
        for (String url : URL_PATH) {
            if (url.equals(requestUri)){
                return true;
            }
        }
        redisTemplate.opsForValue().set(pre,requestUri,30, TimeUnit.SECONDS);
        log.info("" + redisTemplate.opsForValue().get(pre));
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            throw new CustomException("请先登录");
        }
        String user = String.valueOf(redisTemplate.opsForValue().get(token));
        if (StringUtils.isBlank(user)){
            throw new CustomException("请先登录");
        }
        //获取实例
        UserContextHolder<UserEntity> instance = UserContextHolder.getInstance();
        // 保存用户信息到上下文
        UserEntity userEntity = JSONObject.parseObject(user,UserEntity.class);
        HashMap<String,UserEntity> map = new HashMap<>(32);
        map.put("user",userEntity);
        instance.setContext(map);
        // 不放行
        return true;
    }

    /** 之中 **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String pre = RedisKeyUtil.getRequestKey("post");
//        redisTemplate.opsForValue().set(pre,"URL拦截之中",30, TimeUnit.SECONDS);
//        log.info("" + redisTemplate.opsForValue().get(pre));
//        super.postHandle(request,response,handler,modelAndView);
    }

    /** 之后 **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        String pre = RedisKeyUtil.getRequestKey("after");
//        redisTemplate.opsForValue().set(pre,"URL拦截之后",30, TimeUnit.SECONDS);
//        log.info("" + redisTemplate.opsForValue().get(pre));
//        super.afterCompletion(request,response,handler,ex);
        //清除用户信息
        UserContextHolder.getInstance().clear();
        log.info("删除用户信息");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
