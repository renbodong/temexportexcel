package com.zhuiquu.temexportexcel.controller;

import com.zhuiquu.temexportexcel.anno.ExcelField;
import com.zhuiquu.temexportexcel.anno.ExcelModel;
import lombok.Data;

/**
 * Created by Administrator on 2020/5/3 1:17
 */
@Data
@ExcelModel(sheetName = "用户excel",excelName = "用户excel")
public class User {
    @ExcelField(title = "姓名")
    private String username;
    @ExcelField(title = "年龄")
    private int age;
    @ExcelField(title = "性别",enumClassPath = "com.zhuiquu.temexportexcel.controller.SexEnum",codeGetMethod = "getCode",msgGetMethod = "getMsg")
    private String sex;
    @ExcelField(title = "状态",enumClassPath = "com.zhuiquu.temexportexcel.controller.StatusEnum",codeGetMethod = "getCode",msgGetMethod = "getMsg")
    private String status;

    public User(String username, int age, String sex, String status) {
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.status = status;
    }
}
