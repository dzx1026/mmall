package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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

    @Autowired
    private IUserService iUserService;
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
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 登出接口
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> register(User user){
        ServerResponse<String> response=iUserService.register(user);
        return response;
    }

    /**
     * 实时监测email和username是否合法
     */
    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登陆，无法获取用户信息");   //泛型，这个时候的user为空
    }

    /**
     * 忘记密码,返回问题
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验密码问题，问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 重置密码
     * @param username
     * @param newPassword
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetResetPassword(username,newPassword,forgetToken);
    }

    /**
     * 登陆状态下的重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());   //从前端传过来的数据没有userid,也防止越权
        user.setUsername(currentUser.getUsername());  //设置username,防止越权
        ServerResponse response=iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)   //设定只能post请求
    @ResponseBody                    //这个注解用来实现json序列化
    public ServerResponse<User> getInformation(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要用户登陆");
        }
        return iUserService.getInformation(user.getId());
    }
}
