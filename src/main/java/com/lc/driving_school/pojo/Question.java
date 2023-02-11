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
    private String questionName;// 题的名称
    private String questionImage;// 题的图片地址
    private Integer questionType;// 题的类型
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 修改时间
    private Integer isDelete;// 是否删除
}
