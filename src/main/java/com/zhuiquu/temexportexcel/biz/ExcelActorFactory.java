package com.zhuiquu.temexportexcel.biz;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * excel工厂
 * @author rbd
 */
@Component
public class ExcelActorFactory {

    private Map<String,ExcelActuator> map = new ConcurrentHashMap<>();

    ExcelActorFactory(Map<String,ExcelActuator> map){
        this.map = map;
    }

    public ExcelActuator getExcelActrator(String code){
        return map.get(code);
    }

}
