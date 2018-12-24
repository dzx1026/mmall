package com.mmall.service;

/**
 * 用户服务协议     为什么使用接口会更易于扩展?
 */
public interface IUserService {
    Object login(String userName, String password);
}
