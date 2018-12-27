package com.mmall.common;

public class Const {
    public static final String CURRENT_USER="currentUser";

    public interface Role{           //使用内部类起到一个分组的作用，比enum更简洁
        int ROLE_CUSTOMER=0;//普通用户       接口中的常量
        int ROLE_ADMIN=1;//管理员
    }

    public static final String EMAIL="EMAIL";
    public static final String USERNAME="USERNAME";
}
