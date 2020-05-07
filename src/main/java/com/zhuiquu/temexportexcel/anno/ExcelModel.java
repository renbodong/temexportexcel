package com.zhuiquu.temexportexcel.anno;

import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导出实体类注解
 * @author rbd
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelModel {
    /**
     * sheet标题
     *
     * @return
     */
    String sheetName() default "sheet";

    /**
     * exce标题
     * @return
     */
    String excelName() default "excel";

}
