package com.homework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 字段名与类名的映射
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnAnn {
    String value();// 属性名与列名的映射
    boolean isPrimaryKey() default false; //是否为主键
    boolean isAutoIncrement() default false;//是否为自增长
}
