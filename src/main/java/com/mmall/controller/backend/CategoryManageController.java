package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    /**
     * 增加分类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse<String> addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要进行登录");
        }
        //检查是不是管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("当前账号没有权限");
        }
        return iCategoryService.addCategory(parentId,categoryName);
    }

    @RequestMapping("update_category_name")
    @ResponseBody
    public ServerResponse<String> updateCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要进行登录");
        }
        //检查是不是管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("当前账号没有权限");
        }
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }

    /**
     * 获取儿子节点的目录信息
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_children_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要进行登录");
        }
        //检查是不是管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("当前账号没有权限");
        }
        //查找儿子节点
        return iCategoryService.getChildParallelCategory(categoryId);
    }

    /**
     * 获取当前节点的信息以及所有子节点的id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deepchildren_category.do")
    @ResponseBody
    public ServerResponse getGategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要进行登录");
        }
        //检查是不是管理员
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("当前账号没有权限");
        }
        //查找当前节点及所有子节点信息
        return iCategoryService.getGategoryAndDeepChildrenCategory(categoryId);
    }

}
