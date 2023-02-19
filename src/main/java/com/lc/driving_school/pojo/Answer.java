package com.lc.driving_school.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    @TableId
    private Long id;
    private String answerId;// 题库导过来的id
    private String answerExplain	;// 题的解析
    private String answer	;// 题的答案
    private Integer titleType; // 题的类型1-科目一 2-科目四
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 修改时间
    private Integer isDelete;// 是否删除
}
