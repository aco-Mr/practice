package com.aco.practice.demo1.handle;

import com.aco.practice.demo1.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XHaoJian
 * @data 2020/6/14 21:50
 */
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private RedisTemplate<String,Object> redisTemplate;

    /** 之前 **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pre = RedisKeyUtil.getRequestKey("pre");
        redisTemplate.opsForValue().set(pre,"URL拦截之前");
        log.info("" + redisTemplate.opsForValue().get(pre));
        return preHandle(request,response,handler);
    }

    /** 之中 **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String pre = RedisKeyUtil.getRequestKey("post");
        redisTemplate.opsForValue().set(pre,"URL拦截之中");
        log.info("" + redisTemplate.opsForValue().get(pre));
        super.postHandle(request,response,handler,modelAndView);
    }

    /** 之后 **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String pre = RedisKeyUtil.getRequestKey("after");
        redisTemplate.opsForValue().set(pre,"URL拦截之后");
        log.info("" + redisTemplate.opsForValue().get(pre));
        super.afterCompletion(request,response,handler,ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
