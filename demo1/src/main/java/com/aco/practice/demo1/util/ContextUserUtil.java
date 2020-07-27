package com.aco.practice.demo1.util;

import com.aco.practice.demo1.domain.entity.UserEntity;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author XHaoJian
 * @data 2020/7/27 22:33
 */
public class ContextUserUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserEntity getUserInfo(){
        ServletContext servletContext = getRequest().getServletContext();
        Object user = servletContext.getAttribute("user");
        return JSONObject.parseObject(JSONObject.toJSONString(user), UserEntity.class);
    }
}
