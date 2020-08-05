package com.aco.practice.demo1.config;

import com.aco.practice.demo1.handle.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    /**
     * 配置跨域处理
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
                .addMapping("/**")
                // 允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
                .allowedOrigins("*")
                // 允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                //允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
                .allowedHeaders("*")
                //缓存请求的秒数
                .maxAge(3600)
                //是否发送Cookie信息
                .allowCredentials(true);
    }
}
