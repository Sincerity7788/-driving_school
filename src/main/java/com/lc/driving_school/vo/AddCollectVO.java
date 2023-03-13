package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class AddCollectVO {
    private String questionId;// 主表的题目id
    private String questionTitle;// 题目的标题
    private String userId;// 用户id
}

