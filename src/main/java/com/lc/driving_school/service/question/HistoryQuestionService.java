package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lc.driving_school.mapper.HistoryQuestionMapper;
import com.lc.driving_school.pojo.HistoryQuestion;
import com.lc.driving_school.vo.AddHistoryQuestionVO;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HistoryQuestionService {
    // 引入对应上数据库mapper
    private final HistoryQuestionMapper historyQuestionMapper;

    // 添加
    public ResponseVO add(AddHistoryQuestionVO addHistoryQuestionVO){
        ResponseVO responseVO = new ResponseVO();
        if( addHistoryQuestionVO.getUserId() == null || addHistoryQuestionVO.getQuestionId() == null ||
            Objects.equals(addHistoryQuestionVO.getQuestionId(), "") ||
            Objects.equals(addHistoryQuestionVO.getUserId(), "") ){
            // 设置返回值
            responseVO.setCode("-1");
            responseVO.setMessage("userId and questionId 必传！");
            return  responseVO;
        }
        // 查看当前数据库是不是已经存储过这条数据了
        QueryWrapper<HistoryQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", addHistoryQuestionVO.getQuestionId());

        HistoryQuestion historyQuestion1 = historyQuestionMapper.selectOne(wrapper);

        if( historyQuestion1 != null ){
            // 更新次数就行
            UpdateWrapper<HistoryQuestion> objectUpdateWrapper = new UpdateWrapper<>();
            objectUpdateWrapper.eq("id", historyQuestion1.getId());
            objectUpdateWrapper.set("frequency", historyQuestion1.getFrequency() + 1);
            if(Objects.equals(addHistoryQuestionVO.getMistake(), 1)){
                objectUpdateWrapper.set("mistake", historyQuestion1.getMistake() + 1);
            }

            int update = historyQuestionMapper.update(historyQuestion1, objectUpdateWrapper);
            if(update == 1){
                // 设置返回值
                responseVO.setCode("200");
                responseVO.setMessage("保存成功");
            }else{
                // 设置返回值
                responseVO.setCode("-1");
                responseVO.setMessage("保存失败！");
            }

        }else{
            // 准备保存的数据
            HistoryQuestion historyQuestion = new HistoryQuestion();
            historyQuestion.setQuestionId(addHistoryQuestionVO.getQuestionId());
            historyQuestion.setUserId(addHistoryQuestionVO.getUserId());
            historyQuestion.setMistake(addHistoryQuestionVO.getMistake());

            // 插入到数据库
            int insert = historyQuestionMapper.insert(historyQuestion);
            if(insert == 1){
                // 设置返回值
                responseVO.setCode("200");
                responseVO.setMessage("保存成功");
            }else{
                // 设置返回值
                responseVO.setCode("-1");
                responseVO.setMessage("保存失败！");
            }
        }



        return  responseVO;
    }

    // 获取所有
    public ResponseVO getAll(String userId){
        ResponseVO responseVO = new ResponseVO();

        if( userId == null|| userId.equals("")){
            // 设置返回值
            responseVO.setCode("-1");
            responseVO.setMessage("用户id必传！");
        }else{
            // 创建查询条件
            QueryWrapper<HistoryQuestion> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            // 请求数据库
            List<HistoryQuestion> historyQuestions = historyQuestionMapper.selectList(wrapper);

            // 设置返回值
            responseVO.setCode("200");
            responseVO.setMessage("获取成功");
            responseVO.setData(historyQuestions);
        }

        return responseVO;
    }
}
