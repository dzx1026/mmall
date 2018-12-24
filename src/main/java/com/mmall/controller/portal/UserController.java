package com.mmall.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 前台用户类
 */
@Controller
@RequestMapping("/user/")   //当前类的所有方法的请求地址都在/user/下
public class UserController {
    /**
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    /*
    　@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML
　　数据，需要注意的呢，在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public Object login(String username, String password, HttpSession session){



        return null;
    }
}
