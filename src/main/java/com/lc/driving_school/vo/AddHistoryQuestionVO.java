package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class AddHistoryQuestionVO {
    private String userId;// 用户id
    private String questionId;// 题目主表id
    private Integer mistake;// 答错次数
}
