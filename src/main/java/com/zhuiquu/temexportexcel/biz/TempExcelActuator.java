package com.zhuiquu.temexportexcel.biz;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author rbd
 */
@Component("temp")
public class TempExcelActuator extends AbstractExcelActuator{

    @Override
    public void export(Class cla, List datas, HttpServletResponse response) {

    }
}
