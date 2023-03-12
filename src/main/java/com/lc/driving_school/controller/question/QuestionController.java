package com.lc.driving_school.controller.question;

import com.baomidou.mybatisplus.extension.api.R;
import com.lc.driving_school.service.question.QuestionService;
import com.lc.driving_school.vo.GetQuestionVO;
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

    // 删除用户存在redis中的数据
    @ResponseBody
    @PostMapping("/api/v1/quesRen/deleteRedisQuestion")
    public ResponseVO deleteRedisQuestion(@RequestParam String userId){
        return questionService.deleteRedisQuestion(userId);
    }

    // 根据下标获取指定题
    @ResponseBody
    @GetMapping("/api/v1/quesRen/getQuestionIndexData")
    public ResponseVO getQuestionIndexData(@RequestParam String userId, int index){
        return questionService.getQuestionIndexData(userId, index);
    }

    // 模拟考试
    @ResponseBody
    @GetMapping("/api/v1/quesRen/getRandom")
    public ResponseVO getRandom(@RequestParam String userId){
        return questionService.getRandom(userId);
    }

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
    public ResponseVO getTotal(@RequestParam(value = "userId") String userId, @RequestParam(value = "type") String type){
        return questionService.getTotal(userId, type);
    }

    // 获取考题
    @ResponseBody
    @GetMapping("/api/v1/question/getQuestion")
    public ResponseVO getQuestion(GetQuestionVO getQuestionVO){
        System.out.println(getQuestionVO);
        return questionService.getQuestion(getQuestionVO);
    }


}
