package com.zhuiquu.temexportexcel.controller;

import com.zhuiquu.temexportexcel.biz.CommonExcelActuator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/5/3 15:30
 */
@RequestMapping("/excel")
@RestController
public class DemoController {
    @Resource(name = "common")
    private CommonExcelActuator<User> commonExcelActuator;
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public void export(HttpServletResponse response){
        User user = new User("任博栋",25,"1","1");
        User user1 = new User("林正指",24,"0","0");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        commonExcelActuator.export(User.class,list,response);
    }
}
