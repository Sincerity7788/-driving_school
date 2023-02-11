package com.lc.driving_school.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId
    private Long id;// 用户id
    private String userName;// 用户名
    private String nickName;// 昵称
    private String userPassword;// 用户密码
    private String quantity;// 做过多少题
    private String fraction;// 最高分数
    private String mistake;// 错题数量
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 修改时间
    private Integer isDelete;// 是否删除
}
