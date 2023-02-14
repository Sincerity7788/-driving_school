package com.lc.driving_school.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.driving_school.mapper.UserMapper;
import com.lc.driving_school.pojo.User;
import com.lc.driving_school.vo.LoginVO;
import com.lc.driving_school.vo.RegisterVO;
import com.lc.driving_school.vo.ResponseVO;
import com.lc.driving_school.vo.UserLoginBackVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    // 引入操作数据库的mapper
    private final UserMapper userMapper;

    // 登陆方法
    public ResponseVO login(LoginVO loginVO){
        // 定义返回值
        ResponseVO responseVO = new ResponseVO();

        // 创建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 添加查询条件
        wrapper.eq("user_name", loginVO.getUserName()).eq("user_password", loginVO.getPassWord());

        // 执行查询语句
        User user = userMapper.selectOne(wrapper);

        // 判断是否查到了
        if( user != null ){
            responseVO.setCode("200");
            responseVO.setMessage("登陆成功");
            UserLoginBackVO userLoginBackVO = new UserLoginBackVO();
            userLoginBackVO.setUserName(loginVO.getUserName());
            userLoginBackVO.setNickName(user.getNickName());
            userLoginBackVO.setFraction(user.getFraction());
            userLoginBackVO.setMistake(user.getMistake());
            userLoginBackVO.setQuantity(user.getQuantity());
            userLoginBackVO.setToken("123456");
            responseVO.setData(userLoginBackVO);
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("用户名或者密码错误！");
        }
        return responseVO;
    }

    // 注册方法
    public ResponseVO register(RegisterVO registerVO){
        // 定义返回值
        ResponseVO responseVO = new ResponseVO();

        if(  registerVO.getUserName() == null || registerVO.getNickName() == null || registerVO.getPassWord() == null||
                Objects.equals(registerVO.getUserName(), "") || Objects.equals(registerVO.getNickName(), "") || Objects.equals(registerVO.getPassWord(), "")
        ) {
            responseVO.setCode("-1");
            responseVO.setMessage("请填写完整信息注册！");
            return responseVO;
        }

        // 创建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 添加查询条件
        wrapper.eq("user_name", registerVO.getUserName());
        // 查询数据库是否已经存在

        User user = userMapper.selectOne(wrapper);
        if( user == null ){
            // 创建存储的信息
            User user1 = new User();
            user1.setUserName(registerVO.getUserName());
            user1.setNickName(registerVO.getNickName());
            user1.setUserPassword(registerVO.getPassWord());

            // 存储到数据库
            int insert = userMapper.insert(user1);
            if( insert == 1 ){
                responseVO.setCode("200");
                responseVO.setMessage("注册成功，请登陆！");
            }else{
                responseVO.setCode("-1");
                responseVO.setMessage("注册失败！");
            }
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("用户名已存在！");
        }


        return responseVO;
    }




}
