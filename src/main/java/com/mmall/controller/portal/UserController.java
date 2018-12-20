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
    @RequestMapping(value = "login.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public Object login(String username, String password, HttpSession session){



        return null;
    }
}
