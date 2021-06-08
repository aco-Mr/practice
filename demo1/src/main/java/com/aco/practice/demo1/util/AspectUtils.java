package com.aco.practice.demo1.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 切面工具
 * @Author: HaoJianXu
 * @Date: 2021/6/8 16:22
 */
public class AspectUtils {
    /**
     * @param joinPoint
     * @param annotationClass
     * @return
     */
    public static <T extends Annotation> T getTargetMethodAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
        Method targetMethod = getTargetMethod(joinPoint);
        T annotation = targetMethod.getAnnotation(annotationClass);
        return annotation;
    }

    /**
     * @param joinPoint
     * @return
     */
    public static Method getTargetMethod(JoinPoint joinPoint) {
        Object targetObj = joinPoint.getTarget();// 目标对象
        Class<?> targetClass = targetObj.getClass();// 目标对象的class
        Object[] arguments = joinPoint.getArgs(); // 目标方法实参
        Signature signature = joinPoint.getSignature();// 目标方法签名
        String targetMethodName = signature.getName();// 目标方法名
        String targetMethodSignatureLongString = signature.toLongString();
        String targetMethodParamList = targetMethodSignatureLongString.substring(targetMethodSignatureLongString.indexOf("("), targetMethodSignatureLongString.indexOf(")") + 1);

        /*
         * 方法3：
         */
        Method[] methods = targetClass.getMethods();// 目标对象的所有方法
        Method targetMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(targetMethodName)) {// 方法名相同
                int parameterCount = method.getParameterCount();
                if (parameterCount == arguments.length) {// 方法参数个数相同
                    String methodString = method.toString();
                    String methodParamList = methodString.substring(methodString.indexOf("("), methodString.indexOf(")") + 1);
                    if (targetMethodParamList.equals(methodParamList)) {// 方法参数列表相同
                        targetMethod = method;
                        break;
                    }
                }
            }
        }

        return targetMethod;
    }
}
