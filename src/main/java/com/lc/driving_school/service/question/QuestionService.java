package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.driving_school.mapper.HistoryQuestionMapper;
import com.lc.driving_school.mapper.QuestionMapper;
import com.lc.driving_school.mapper.UserMapper;
import com.lc.driving_school.pojo.HistoryQuestion;
import com.lc.driving_school.pojo.Question;
import com.lc.driving_school.pojo.User;
import com.lc.driving_school.vo.GetQuestionVO;
import com.lc.driving_school.vo.QuestionTotalVO;
import com.lc.driving_school.vo.QuestionVO;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionService {
    // 引入操作数据库的mapper
    private final QuestionMapper questionMapper;
    // 查询历史数据
    private final HistoryQuestionMapper historyQuestionMapper;

    // 查询用户的mapper
    private final UserMapper userMapper;

    // 查询总数量
    public ResponseVO getTotal(String userId){
        ResponseVO responseVO = new ResponseVO();

        try{
            // 从数据库查询
            Integer integer = questionMapper.selectCount(null);
            QuestionTotalVO questionTotalVO = new QuestionTotalVO();
            questionTotalVO.setTotal(integer);
            // 根据用户查询一下当前用户做过多少题
            User user = userMapper.selectById(userId);
            if( user == null  ){
                responseVO.setCode("-1");
                responseVO.setMessage("用户id错误!");
                responseVO.setData(false);
            }else{
                questionTotalVO.setQuantity(user.getQuantity());
            }
            responseVO.setCode("200");
            responseVO.setMessage("查询成功");
            responseVO.setData(questionTotalVO);
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
        // 创建分页对象
        Page<Question> objectPage = new Page<>(getQuestionVO.getPageNum(), getQuestionVO.getPageSize() );

        // 判断当前是错题练习
        if(Objects.equals(getQuestionVO.getOrderType(), "3")){
            // 根据用户id获取到错题练习的数据
            QueryWrapper<HistoryQuestion> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("user_id", getQuestionVO.getUserId());
            objectQueryWrapper.gt("mistake", 0);

            Page<HistoryQuestion> objectPage1 = new Page<>(getQuestionVO.getPageNum(), getQuestionVO.getPageSize());

            historyQuestionMapper.selectPage(objectPage1, objectQueryWrapper);
            if( objectPage1.getTotal()  > 0 ){
                QueryWrapper<Question> question_id = new QueryWrapper<>();
                question_id.eq("question_id", objectPage1.getRecords().get(0).getQuestionId());

                objectPage.setCurrent(1);
                questionMapper.selectPage(objectPage, question_id);

                objectPage.setTotal(objectPage1.getTotal());
                objectPage.setPages(objectPage1.getPages());

                responseVO.setData(objectPage);
                responseVO.setCode("200");
                responseVO.setMessage("获取成功");
                return responseVO;
            }else{
                responseVO.setCode("-1");
                responseVO.setMessage("获取失败");
            }

        }

        // 创建查询
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("type", getQuestionVO.getType());

        // 查询数据库
        try{
            questionMapper.selectPage(objectPage, wrapper);
            responseVO.setData(objectPage);
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
