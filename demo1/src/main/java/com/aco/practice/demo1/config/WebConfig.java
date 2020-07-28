package com.aco.practice.demo1.config;

import com.aco.practice.demo1.handle.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author XHaoJian
 * @data 2020/6/14 21:45
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String[] URL_PATH = {"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/user/save","/login/form","/aco/error","/aco/csrf"};

    //新建一个拦截类注入到spring容器
    @Bean
    public HandlerInterceptor getRequestInterceptor(){
        //返回自定义的拦截类
        return new RequestInterceptor();
    }

    /** 配置拦截器 **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                // 指定拦截类
                .addInterceptor(getRequestInterceptor())
                .excludePathPatterns(URL_PATH);
                // 指定拦截路径
//                .addPathPatterns("/**")
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
