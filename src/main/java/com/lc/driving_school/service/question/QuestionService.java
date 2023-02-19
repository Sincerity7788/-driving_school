package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.driving_school.mapper.QuestionMapper;
import com.lc.driving_school.pojo.Question;
import com.lc.driving_school.vo.GetQuestionVO;
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

    // 查询总数量
    public ResponseVO getTotal(){
        ResponseVO responseVO = new ResponseVO();

        try{
            // 从数据库查询
            Integer integer = questionMapper.selectCount(null);

            responseVO.setCode("200");
            responseVO.setMessage("查询成功");
            responseVO.setData(integer);
        }catch (Error error){
            responseVO.setCode("-1");
            responseVO.setMessage("查询数据库出错!");
            responseVO.setData(false);
        }
        return responseVO;
    }

    // 根据类型查询指定题
    public ResponseVO getQuestion(GetQuestionVO getQuestionVO){
        ResponseVO responseVO = new ResponseVO();
        // 创建查询
        QueryWrapper<Question> wrapper = new QueryWrapper<>();

        wrapper.eq("type", getQuestionVO.getType());
        wrapper.last("limit " + getQuestionVO.getPageNum() + ", " + getQuestionVO.getPageSize());
        // 查询数据库
        try{
            Question questions = questionMapper.selectOne(wrapper);

            responseVO.setData(questions);
            responseVO.setCode("200");
            responseVO.setMessage("获取成功");
        }catch (Error error){
            responseVO.setCode("-1");
            responseVO.setMessage("获取失败");
        }

        return responseVO;
    }

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
        wrapper.eq("question_id", questionVO.getQuestionId());

        // 查询是否存在同一个题目的题
        Question question = questionMapper.selectOne(wrapper);
        if(question == null){
            // 创建题目实例
            Question question1 = new Question();
            question1.setQuestionId(questionVO.getQuestionId());
            question1.setTitle(questionVO.getTitle());
            question1.setRank(questionVO.getRank());
            question1.setType(questionVO.getType());
            question1.setOp1(questionVO.getOp1());
            question1.setOp2(questionVO.getOp2());
            question1.setOp3(questionVO.getOp3());
            question1.setOp4(questionVO.getOp4());
            question1.setTitleType(questionVO.getTitleType());
            question1.setTitlePic(questionVO.getTitlePic());

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
