package com.zhuiquu.temexportexcel.biz;

import com.zhuiquu.temexportexcel.anno.ExcelField;
import com.zhuiquu.temexportexcel.anno.ExcelModel;
import com.zhuiquu.temexportexcel.enums.EnumHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

/**
 * excel工具类
 *
 * @author rbd
 */
@Slf4j
@Component("common")
public class CommonExcelActuator<T> extends AbstractExcelActuator<T> {
    /**
     * sheet标题
     */
    private String sheetName;
    /**
     * excel标题
     */
    private String excelName;

    /**
     * excel工作目录
     */
    private Workbook wb;

    /**
     * 当前sheet
     */
    private Sheet sheet;

    /**
     * 列数
     */
    private int col = 0;

    /**
     * 行数
     */
    private int row = 0;


    /**
     * excel导出方法
     * <p>
     * 通过T注解excelField注解获取标题
     *
     * @param datas 列表数据
     */
    @Override
    public void export(Class<T> cla, List<T> datas, HttpServletResponse response) {
        try {
            createWorkBook(cla);
            creatSheetAndInsertData(cla, datas);
            write2Res(response.getOutputStream());
            setResponseContentType(response);
        } catch (IOException e) {
            log.debug("HttpServletResponse获取输出流异常");
            e.printStackTrace();
        }
    }

    /**
     * 设置输出流
     */
    private void setResponseContentType(HttpServletResponse response) throws UnsupportedEncodingException {
        log.info("开始设置response");
        String excelNameStart = excelName.substring(0, excelName.indexOf("."));
        String excelNameEnd = excelName.substring(excelName.indexOf("."));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((excelNameStart).getBytes(), "iso-8859-1")+excelNameEnd);
    }

    /**
     * 写入response的输出流
     *
     * @param outputStream
     */
    private void write2Res(OutputStream outputStream) {
        try {
            wb.write(outputStream);
            outputStream.flush();
            log.info("excel导出完成");
        } catch (IOException e) {
            log.info("excel导出写入输出流出错");
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 创建sheet
     *
     * @param cla 通过cla属性注解ExcelField注解获取字段标题
     */
    private void creatSheetAndInsertData(Class<T> cla, List<T> datas) {
        sheet = wb.createSheet(sheetName);
        Field[] fs = cla.getDeclaredFields();
        for (; row < datas.size() + 1; row++) {
            Row head = sheet.createRow(row);
            for (; col < fs.length; col++) {
                ExcelField ef = fs[col].getAnnotation(ExcelField.class);
                if (row == 0) {
                    //标题
                    createCell(head, ef, ef.title());
                } else {
                    //内容
                    String returnStr = getReturnByFieldGetMethod(datas.get(row - 1), fs[col]);
                    createCell(head, ef, returnStr);
                }

            }
            col = 0;
        }

    }

    /**
     * 根据成员变量获取该成员变量 get方法返回值
     *
     * @param t
     * @param field 成员变量
     */
    private String getReturnByFieldGetMethod(T t, Field field) {
        try {
            Class<T> cla = (Class<T>) t.getClass();
            String fieldName = field.getName();
            String fieldGetName = buildGetFieldGetName(fieldName);
            Method method = cla.getDeclaredMethod(fieldGetName);
            return method.invoke(t).toString();
        } catch (Exception e) {
            log.debug("excel导出根据成员变量获取get方法返回值异常");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 构建成员的get方法
     *
     * @param fieldName 成员变量名称
     */
    private String buildGetFieldGetName(String fieldName) {
        String startField = fieldName.substring(0, 1).toUpperCase();
        return "get" + startField + fieldName.substring(1);
    }


    /**
     * 创建单元格
     *
     * @param head  row
     * @param ef    ExcelField
     * @param value 单元格值
     */
    private void createCell(Row head, ExcelField ef, String value) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Cell cell = head.createCell(col);
        try {
            //设置内容的时候字典项为空或者设置表头的时候，就设置title
            if (row == 0 || StringUtils.isEmpty(ef.enumClassPath()) || StringUtils.isEmpty(ef.codeGetMethod()) || StringUtils.isEmpty(ef.msgGetMethod())) {
                cell.setCellValue(value);
            } else {
                Class<?> cla = Class.forName(ef.enumClassPath());
                String values = EnumHelper.getEnumByClassAndCode(cla, ef.codeGetMethod(), ef.msgGetMethod(), value);
                cell.setCellValue(values);
            }

            cell.setCellStyle(cellStyle);
        } catch (ClassNotFoundException e) {
            log.debug("通过传过来的枚举类路径获取类的class报错");
            e.printStackTrace();
        }
    }

    /**
     * 创建excel工作目录
     *
     * @param cla 通过cla类属性注解ExcelModel注解获取excel标题
     */
    private void createWorkBook(Class<T> cla) {
        ExcelModel excelModel = cla.getAnnotation(ExcelModel.class);
        excelName = excelModel.excelName();
        sheetName = excelModel.sheetName();
        if (!excelName.contains(".xls") && !excelName.contains(".xlsx")) {
            excelName += ".xlsx";
        }
        if (excelName.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
    }

}
