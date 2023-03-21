package com.lc.driving_school.controller.historyTest;

import com.lc.driving_school.service.historyTest.HistoryTestService;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
public class HistoryTestController {

    @Resource
    private HistoryTestService historyTestService;

    /**
     * 添加考试记录
     * @params userId
     * @params userName
     */
    @ResponseBody
    @PostMapping("/api/v1/historyTest/addHistoryTest")
    public ResponseVO addHistoryTest(@RequestParam String userId, @RequestParam  String userName,@RequestParam  int finish){
        return historyTestService.addHistoryTest(userId, userName, finish);
    }

}
