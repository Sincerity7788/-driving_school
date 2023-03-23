package com.lc.driving_school.controller.question;

import com.lc.driving_school.service.question.HistoryQuestionService;
import com.lc.driving_school.vo.AddHistoryQuestionVO;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
public class HistoryQuestionController {
    // 引入service
    @Resource
    private HistoryQuestionService historyQuestionService;

    /**
     * 获取数据
     * @param userId
     * @param current
     * @param pageSize
     * @return ResponseVO
     */
    @ResponseBody
    @GetMapping("/api/v1/historyQuestion/getList")
    public ResponseVO getList(@RequestParam String userId,@RequestParam int current,@RequestParam(defaultValue = "10") int pageSize){
        return historyQuestionService.getList(userId, current, pageSize);
    }

    // 获取全部
    @ResponseBody
    @GetMapping("/api/v1/historyQuestion/getAll")
    public ResponseVO getAll(@RequestParam String userId){
        return historyQuestionService.getAll(userId);
    }

    // 添加
    @ResponseBody
    @PostMapping("/api/v1/historyQuestion/add")
    public ResponseVO add(@RequestBody AddHistoryQuestionVO addHistoryQuestionVO){
        return historyQuestionService.add(addHistoryQuestionVO);
    }
}
