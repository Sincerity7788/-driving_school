package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.driving_school.mapper.AnswerMapper;
import com.lc.driving_school.pojo.Answer;
import com.lc.driving_school.vo.AddAnswerVO;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    // 导入mapper
    private final AnswerMapper answerMapper;

    // 添加
    public ResponseVO add(AddAnswerVO addAnswerVO){
        ResponseVO responseVO = new ResponseVO();

        // 创建查询条件
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("answer_id", addAnswerVO.getAnswerId());

        Answer answer = answerMapper.selectOne(wrapper);
        if(answer == null){
            // 创建数据
            Answer answer1 = new Answer();
            answer1.setAnswer(addAnswerVO.getAnswer());
            answer1.setAnswerId(addAnswerVO.getAnswerId());
            answer1.setAnswerExplain(addAnswerVO.getAnswerExplain());
            answer1.setTitleType(addAnswerVO.getTitleType());

            // 存入数据库
            int insert = answerMapper.insert(answer1);
            if(insert == 1){
                responseVO.setCode("200");
                responseVO.setMessage("答案保存成功！");
            }else{
                responseVO.setCode("-1");
                responseVO.setMessage("数据插入失败！");
            }


        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("答案重复！");
        }

        return responseVO;
    }
}
