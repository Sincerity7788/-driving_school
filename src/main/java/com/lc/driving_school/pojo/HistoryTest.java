package com.lc.driving_school.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryTest {
    @TableId
    private Long id;
    private Integer fraction;// 考试的分数
    private Integer finish;// 考试是否完成
    private String time;// 考试的时间
    private String userId;// 考试的用户Id
    private String userName;// 考试的用户名
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;// 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;// 更新时间
    private Integer isDelete;// 是否删除
}
