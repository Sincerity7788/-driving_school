package com.lc.driving_school.controller.collect;

import com.lc.driving_school.service.collect.CollectService;
import com.lc.driving_school.vo.AddCollectVO;
import com.lc.driving_school.vo.ResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@CrossOrigin(origins = "*")
public class CollectController {
    // services
    @Resource
    private CollectService collectService;

    // 添加
    @ResponseBody
    @PostMapping("/api/v1/collect/addCollect")
    public ResponseVO addCollect(@RequestBody AddCollectVO addCollectVO){
        return collectService.addCollect(addCollectVO);
    }

    // 判断指定用户收藏过该题
    @ResponseBody
    @GetMapping("/api/v1/collect/userHasQuestion")
    public  ResponseVO userHasQuestion(@RequestParam String userId, String questionId){
        return collectService.userHasCollect(userId, questionId);
    }

    // 删除
    @ResponseBody
    @GetMapping("/api/v1/collect/deleteUserCollect")
    public ResponseVO deleteUserCollect(@RequestParam String userId, String questionId){
        return collectService.deleteUserCollect(userId, questionId);
    }
}
