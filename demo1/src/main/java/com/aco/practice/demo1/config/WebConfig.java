package com.aco.practice.demo1.config;

import com.aco.practice.demo1.handle.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author XHaoJian
 * @data 2020/6/14 21:45
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** 配置拦截器 **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                // 实现拦截器
                .addInterceptor(new RequestInterceptor())
                // 添加拦截路径
                .addPathPatterns("/**");
    }
}
