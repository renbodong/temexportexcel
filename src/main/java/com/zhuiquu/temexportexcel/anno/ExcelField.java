package com.zhuiquu.temexportexcel.anno;

import org.springframework.beans.factory.annotation.Required;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author rbd
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    /**
     * 导出的字段标题
     *
     * @return
     */
    String title() default "";

    /**
     * 传入枚举类的路径
     *
     * @return
     */
    String enumClassPath() default "";

    /**
     * 枚举类的code  get方法名称
     * @return
     */
    String codeGetMethod() default "";

    /**
     * 枚举类的msg get方法名称
     * @return
     */
    String msgGetMethod() default "";
}
