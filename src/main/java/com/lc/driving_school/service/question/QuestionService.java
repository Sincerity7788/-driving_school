package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.driving_school.mapper.QuestionMapper;
import com.lc.driving_school.pojo.Question;
import com.lc.driving_school.vo.QuestionVO;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    // 引入操作数据库的mapper
    private final QuestionMapper questionMapper;

    // 查询所有
    public ResponseVO getAllList(){
        ResponseVO responseVO = new ResponseVO();

        List<Question> questions = questionMapper.selectList(null);

        responseVO.setData(questions);
        responseVO.setMessage("查询成功");
        responseVO.setCode("200");

        return responseVO;
    }

    // 添加
    public ResponseVO addQuestion(QuestionVO questionVO){
        ResponseVO responseVO = new ResponseVO();

        // 创建查询条件
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("question_name", questionVO.getQuestionName());

        // 查询是否存在同一个题目的题
        Question question = questionMapper.selectOne(wrapper);
        if(question == null){
            // 创建题目实例
            Question question1 = new Question();
            question1.setQuestionName(questionVO.getQuestionName());
            question1.setQuestionType(questionVO.getQuestionType());
            question1.setQuestionImage(questionVO.getQuestionImage());

            // 插入数据库
            int insert = questionMapper.insert(question1);
            if(insert == 1){
                responseVO.setCode("200");
                responseVO.setMessage("题目保存成功！");
            }else{
                responseVO.setCode("-1");
                responseVO.setMessage("数据插入失败！");
            }
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("题目重复！");
        }

        return responseVO;
    }

}
