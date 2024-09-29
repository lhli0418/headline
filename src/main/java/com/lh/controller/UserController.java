package com.lh.controller;

import com.lh.pojo.User;
import com.lh.service.UserService;
import com.lh.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UserController
 * package: com.lh.controller
 * Description:
 *
 * @Author lh
 * @Create 2024/9/27 13:46
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 登录需求
     * 地址: /user/login
     * 方式: post
     * 参数:
     *    {
     *     "username":"zhangsan", //用户名
     *     "userPwd":"123456"     //明文密码
     *    }
     * 返回:
     *   {
     *    "code":"200",         // 成功状态码
     *    "message":"success"   // 成功状态描述
     *    "data":{
     *         "token":"... ..." // 用户id的token
     *     }
     *  }
     *
     * 大概流程:
     *    1. 账号进行数据库查询 返回用户对象
     *    2. 对比用户密码(md5加密)
     *    3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     *    4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     */
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result userInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){

        Result result = userService.checkLogin(token);
        return result;
    }

}
