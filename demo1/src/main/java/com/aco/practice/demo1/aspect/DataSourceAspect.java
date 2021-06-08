package com.aco.practice.demo1.aspect;

import com.aco.practice.demo1.domain.annotations.DataSource;
import com.aco.practice.demo1.holder.DynamicDataSourceHolder;
import com.aco.practice.demo1.util.AspectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 数据源AOP切面定义
 * @Author: HaoJianXu
 * @Date: 2021/6/8 15:44
 */
@Component
@Aspect
@Slf4j
public class DataSourceAspect {
    //需要切换数据源的方法
    @Pointcut("@annotation(com.aco.practice.demo1.domain.annotations.DataSource)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取注解值
        DataSource dataSource = AspectUtils.getTargetMethodAnnotation(joinPoint, DataSource.class);
        String dataSourceName = dataSource.name();
        //设置线程共享中的数据源名称
        DynamicDataSourceHolder.putDataSource(dataSourceName);
        log.info("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
        Object proceed = joinPoint.proceed();
        //清除线程共享中的数据源名称
        DynamicDataSourceHolder.removeDataSource();
        return proceed;
    }

}
