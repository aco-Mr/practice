package com.homework;


@TableAnn("a_student")     // 如果表名与类名不一致，就配置＠Table注解
public class Student {

    private String stuName;
    private String stuSex;
    private int stuAge;
    @ColumnAnn("pic_path")   // 如果字段名与属性名不一致，就配置@Column注解
    private String picPath;
    @ColumnAnn(value = "stuNo",isPrimaryKey = true,isAutoIncrement = false)
    private int stuNo;

    public Student() {
    }

    public Student(int stuNo, String stuName, String stuSex, int stuAge, String picPath) {
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuSex = stuSex;
        this.stuAge = stuAge;
        this.picPath = picPath;
    }

    public int getStuNo() {
        return stuNo;
    }

    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public int getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNo=" + stuNo +
                ", stuName='" + stuName + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuAge=" + stuAge +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
