package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class QuestionVO {
    private String questionName;// 题的名称
    private String questionImage;// 题的图片地址
    private Integer questionType;// 题的类型
}
