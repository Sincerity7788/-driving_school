package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class UserLoginBackVO {
    private String userName;// 用户名
    private String nickName;// 用户昵称
    private String token;// 用户登录凭证
    private String quantity;// 做过多少题
    private String fraction;// 最高分数
    private String mistake;// 错题数量
}
