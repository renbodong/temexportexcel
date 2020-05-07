package com.zhuiquu.temexportexcel.biz;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author rbd
 */
public interface ExcelActuator<T> {
    void export(Class<T> cla, List<T> datas, HttpServletResponse response);
}
