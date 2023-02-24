package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class AnswerInfo {
    private String answerExplain; // 题解
    private String answer; // 答案
    private Integer frequency;// 做过次数
    private Integer mistake;// 答错次数
    private boolean isRight; // 是否答对
}
