package com.lc.driving_school.controller.question;

import com.lc.driving_school.service.question.AnswerService;
import com.lc.driving_school.vo.AddAnswerVO;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
public class AnswerController {
    // 引入service
    @Resource
    private AnswerService answerService;

    // 添加
    @ResponseBody
    @PostMapping("/api/v1/answer/add")
    public ResponseVO add(@RequestBody AddAnswerVO addAnswerVO){
        return answerService.add(addAnswerVO);
    }
}
