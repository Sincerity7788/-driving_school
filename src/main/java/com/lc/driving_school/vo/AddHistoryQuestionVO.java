package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class AddHistoryQuestionVO {
    private String userId;// 用户id
    private String id;// 题目主表id
    private String questionId;// 题目id
    private String answer;// 答案
}
