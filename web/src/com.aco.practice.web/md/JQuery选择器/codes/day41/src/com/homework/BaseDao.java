package com.homework;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {

    public int execueUpdate(String sql,Object... objs){
        return 0;
    }

    // 执行增、删、改操
    public static int execueUpdate(Object obj){
        Object[] objs = genericByInsert(obj);
        String sql = (String) objs[0];
        List<Object> paramList = (List<Object>) objs[1];
        System.out.println("sql="+sql);
        System.out.println("paramList="+paramList);

//        Connection conn = null;
//        PreparedStatement prs = null;
//
//        // 1、获得连接对象
//        conn = this.getConnection();
//        // 2、获得语句集
//        prs = conn.prepareStatement(sql);
//        // 3、设置占位符的值
//        if(paramObjs.length>0){
//
//        }
        return 0;
    }

    public static Object[] genericByInsert(Object obj){
        Object[] objs = new Object[2];
        String sql = "INSERT INTO ${tableName}(${COLUMNS}) VALUES(${VALUES}) ";
        List<Object> paramList = new ArrayList<>();

        // 1、获得Class
        Class clazz = obj.getClass();
        // 获得表名
        sql = replaceTableName(sql, clazz);

        // 2、获得所有的成员属性
        Field[] fields = clazz.getDeclaredFields();
        if(fields!=null && fields.length>0){

            StringBuffer columnsStr = new StringBuffer();//字段列表
            StringBuffer placeHolderStr = new StringBuffer();//占位符列表
            for(Field f:fields){
                Object value = null;
                try {
                    f.setAccessible(true);
                    // 2.3、同时获得占位符的值存入集合中
                    value = f.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                // 2.1、生成字段列表
                String columnName = f.getName();// 先赋值列名=属性名
                ColumnAnn columnAnn = f.getDeclaredAnnotation(ColumnAnn.class);
                if(columnAnn!=null){
                    columnName = columnAnn.value();

                    // 如果isPrimaryKey=true,isAuto=true，主键字段不加入insert
                    // 如果isPrimaryKey=true,isAuto=false，主键字段加入insert
                    boolean isPrimaryKey = columnAnn.isPrimaryKey();
                    boolean isAutoIncrement = columnAnn.isAutoIncrement();
                    if(isPrimaryKey && isAutoIncrement) continue;
                }
                columnsStr.append(columnName+",");

                // 2.2、生成占位符列表
                placeHolderStr.append(" ?,");
                paramList.add(value);//将占位符的值添加到集合中

            }
            // 去掉末尾逗号
            columnsStr.delete(columnsStr.length()-1,columnsStr.length());
            placeHolderStr.delete(placeHolderStr.length()-1,placeHolderStr.length());
            // 替换占位符${COLUMNS}
            sql = sql.replace("${COLUMNS}",columnsStr.toString());
            // 替换占位符${VALUES}
            sql = sql.replace("${VALUES}",placeHolderStr.toString());
        }
        // 返回结果赋值
        objs[0] = sql;
        objs[1] = paramList;
        return objs;
    }

    private static String replaceTableName(String sql, Class clazz) {
        String tableName = clazz.getSimpleName();
        // 获得类名上的注解
        Annotation classAnn = clazz.getDeclaredAnnotation(TableAnn.class);
        if(classAnn!=null && classAnn instanceof TableAnn){
            TableAnn tableAnn = (TableAnn) classAnn;
            tableName = tableAnn.value();
        }
        // 替换字符串中的占位符${tablename}
        sql = sql.replace("${tableName}",tableName);
        return sql;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setStuNo(10);
        student.setStuName("zs");
        execueUpdate(student);
    }
}
