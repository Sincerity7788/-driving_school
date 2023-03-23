package com.lc.driving_school.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryQuestion {
    @TableId
    private Long id;
    private String questionId;// 主表的题目id
    private String questionTitle;// 题目标题
    private Integer type;// 1科目一  2科目四
    private Integer frequency;// 做过次数
    private Integer mistake;// 答错次数
    private String userId;// 用户id
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 修改时间
    private Integer isDelete;// 是否删除
}
