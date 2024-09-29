package com.lh.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.pojo.User;
import com.lh.service.UserService;
import com.lh.mapper.UserMapper;
import com.lh.utils.JwtHelper;
import com.lh.utils.MD5Util;
import com.lh.utils.Result;
import com.lh.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 86191
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-09-27 00:09:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     *      *    1. 账号进行数据库查询 返回用户对象
     *      *    2. 对比用户密码(md5加密)
     *      *    3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     *      *    4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     * @return Result封装
     */
    @Override
    public Result login(User user) {
        // 根据账号查询
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(userLambdaQueryWrapper);

        // 账号判断
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        // 密码判断
        if (!StringUtils.isEmpty(user.getUserPwd()) &&
        MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            // 账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(loginUser.getUid().longValue());
            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        // 密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result checkLogin(String token) {

        boolean expiration = jwtHelper.isExpiration(token);
        // 没有传入或过期 未登录
        if (expiration || StringUtils.isEmpty(token)) {
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);
    }

    @Override
    public Result getUserInfo(String token) {

        // 判断是否有效期
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            // true过期，直接返回未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        // 获取token对应用户
        int loginUserId = jwtHelper.getUserId(token).intValue();

        // 查询数据
        User loginUser = userMapper.selectById(loginUserId);

        if (loginUser != null){
            // 响应密码为null
            loginUser.setUserPwd(null);
            Map data = new HashMap();
            data.put("loginUser",loginUser);
            return Result.ok(data);
        }

        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {

        LambdaQueryWrapper<User> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());

//        查找注册用户是否存在 //selectCount 检查条数
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if (loginUser != null) {
            // 存在
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        // 不存在
        // 密码加密
        String encrypt = MD5Util.encrypt(user.getUserPwd());
        user.setUserPwd(encrypt);
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);

    }

    /**
     * 检查账号是否存在
     * @param username 账号
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        if (user != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }
}




