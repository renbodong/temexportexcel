package com.zhuiquu.temexportexcel.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author rbd
 */
public class EnumHelper {
    /**
     * indexOf,传入的参数ordinal指的是需要的枚举值在设定的枚举类中的顺序，以0开始
     * T
     * @param clazz
     * @param ordinal
     * @return
     * @author   rbd
     */
    public static <T extends Enum<T>> T indexOf(Class<T> clazz, int ordinal){
        return (T) clazz.getEnumConstants()[ordinal];
    }
    /**
     * nameOf,传入的参数name指的是枚举值的名称，一般是大写加下划线的
     * T
     * @param clazz
     * @param name
     * @return Enum T
     * @author   rbd
     */
    public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name){

        return (T) Enum.valueOf(clazz, name);
    }

    /**
     * 使用枚举类型对应的code获取枚举类型
     * T
     * @param clazz   枚举类的class
     * @param getCode  传入的codee的get方法
     * @param code 传入的typeName值，这个方法为String类型
     * @return
     * @author   rbd
     */
    public static String getEnumByClassAndCode(Class<?> clazz,String getCode, String getMsg,String code) {
        //获取所有常量
       Object[] objects =  clazz.getEnumConstants();
        try {
            Method getCodeMethod = clazz.getDeclaredMethod(getCode);
            Method getMsgMethod = clazz.getDeclaredMethod(getMsg);
            for(Object obj : objects){
                if(code.equals( getCodeMethod.invoke(obj))){
                    return (String) getMsgMethod.invoke(obj);
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return code;
    }

}
