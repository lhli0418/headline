package com.lh.service;

import com.lh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.utils.Result;

/**
* @author 86191
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-09-27 00:09:56
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录接口
     * @param user 用户
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查用户账号信息是否存在
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册接口
     * @param user
     * @return
     */
    Result regist(User user);

    /**
     * 检查登录接口
     * @return
     */
    Result checkLogin(String token);
}
