package com.lc.driving_school.vo;

import lombok.Data;

@Data
public class ResponseVO {
    private String code;
    private String message;
    private Object data;
}
