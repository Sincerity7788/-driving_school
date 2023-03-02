package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class GetQuestionVO {
    private String type;// 题的类型，1 科目一 2 科目四
    private String orderType;// 顺序练习还是随机 1-顺序 2-随机 3-错题
    private String userId;// 顺序练习还是随机 1-顺序 2-随机 3-错题
    private Integer pageNum;// 当前页
    private Integer pageSize;// 当前页数量

}
