package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class AddAnswerVO {
    private String answerId;// 题库导过来的id
    private String answerExplain;// 题的解析
    private String answer;// 题的答案
    private Integer titleType; // 题的类型1-科目一 2-科目四
}
