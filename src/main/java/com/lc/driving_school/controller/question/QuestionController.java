package com.lc.driving_school.controller.question;

import com.lc.driving_school.service.question.QuestionService;
import com.lc.driving_school.vo.QuestionVO;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
public class QuestionController {
    // 加载资源
    @Resource
    private QuestionService questionService;

    // 添加
    @ResponseBody
    @PostMapping("/api/v1/question/add")
    public ResponseVO add(@RequestBody QuestionVO questionVO){
        return questionService.addQuestion(questionVO);
    }

    // 查询列表
    @ResponseBody
    @GetMapping("/api/v1/question/getAllList")
    public ResponseVO getAllList(){
        return questionService.getAllList();
    }

    // 查询总数
    @ResponseBody
    @GetMapping("/api/v1/question/getTotal")
    public ResponseVO getTotal(){
        return questionService.getTotal();
    }

}
