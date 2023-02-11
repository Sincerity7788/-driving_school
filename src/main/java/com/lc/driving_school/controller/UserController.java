package com.lc.driving_school.controller;

import com.lc.driving_school.service.UserService;
import com.lc.driving_school.vo.LoginVO;
import com.lc.driving_school.vo.RegisterVO;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller // 加这个注解是为了告诉springboot这个对象是Controller
@CrossOrigin(origins = "*")// 允许跨域请求
public class UserController {
    @Resource // 加载资源
    private UserService userService;

    // 登陆
    @ResponseBody
    @PostMapping("/api/v1/user/login")
    public ResponseVO login(@RequestBody LoginVO loginVO){
        return userService.login(loginVO);
    }

    // 注册
    @ResponseBody
    @PostMapping("/api/v1/user/register")
    public ResponseVO register(@RequestBody RegisterVO registerVO){
        return userService.register(registerVO);
    }
}
