package com.lc.driving_school.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Question {
    @TableId
    private Long id;
    private String questionId;// 题库导过来的id
    private String title;// 题目内容
    private Integer rank;// 考题等级，1 - c1/c2/c3 小车 2 - A2/B2 货车 3 - A1/A3/B1 卡车 4 - D/E/F 摩托车
    private Integer type;// 考题类型 1 科目一 2 科目四
    private String op1;// 第一个选项内容！如果题目是单选题，那么这个就是正确的选项内容描述
    private String op2;// 第二个选项内容！如果题目是单选题，那么这个就是错误的选项内容描述
    private String op3;// 第三个选项内容！如果题目是单选题，这个字段返回空
    private String op4;// 第四个选项内容！如果题目是单选题，这个字段返回空
    private String titleType;// 题目类型 1 是单选 2 是判断题 3 是多选题
    private String titlePic	;// 题干插图 不是一定会有
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 修改时间
    private Integer isDelete;// 是否删除
}
