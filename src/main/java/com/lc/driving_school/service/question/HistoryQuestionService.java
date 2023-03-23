package com.lc.driving_school.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.driving_school.mapper.AnswerMapper;
import com.lc.driving_school.mapper.HistoryQuestionMapper;
import com.lc.driving_school.mapper.UserMapper;
import com.lc.driving_school.pojo.Answer;
import com.lc.driving_school.pojo.HistoryQuestion;
import com.lc.driving_school.pojo.User;
import com.lc.driving_school.vo.AddHistoryQuestionVO;
import com.lc.driving_school.vo.AnswerInfo;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HistoryQuestionService {
    // 引入对应上数据库mapper
    private final HistoryQuestionMapper historyQuestionMapper;

    // 答案库的mapper
    private final AnswerMapper answerMapper;

    // 用户的mapper
    private final UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取数据
     * @param userId
     * @param current
     * @param pageSize
     * @return ResponseVO
     */
    public ResponseVO getList(String userId, int current, int pageSize){
        ResponseVO responseVO = new ResponseVO();
        // 创建分页对象
        Page<HistoryQuestion> historyQuestionPage = new Page<>();
        historyQuestionPage.setCurrent(current);
        historyQuestionPage.setSize(pageSize);

        QueryWrapper<HistoryQuestion> historyQuestionQueryWrapper = new QueryWrapper<>();
        historyQuestionQueryWrapper.eq("user_id", userId);
        historyQuestionQueryWrapper.gt("mistake", 0);

        // 获取数据
        IPage<HistoryQuestion> historyQuestionIPage = historyQuestionMapper.selectPage(historyQuestionPage, historyQuestionQueryWrapper);
        if( historyQuestionIPage != null ){
            responseVO.setData(historyQuestionIPage);
            responseVO.setMessage("获取成功");
            responseVO.setCode("200");
        }else{
            responseVO.setData(false);
            responseVO.setMessage("获取失败");
            responseVO.setCode("-1");
        }


        return responseVO;
    }

    // 添加
    public ResponseVO add(AddHistoryQuestionVO addHistoryQuestionVO){
        ResponseVO responseVO = new ResponseVO();

        // 查看当前数据库是不是已经存储过这条数据了
        QueryWrapper<HistoryQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", addHistoryQuestionVO.getQuestionId());

        // 查询一下答案
        QueryWrapper<Answer> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("answer_id", addHistoryQuestionVO.getQuestionId());
        Answer answer = answerMapper.selectOne(objectQueryWrapper);

        if( answer == null ){
            // 设置返回值
            responseVO.setCode("-1");
            responseVO.setMessage("答案数据库查询失败！");
            return responseVO;
        }

        // 当前题是否答对
        boolean flag = Objects.equals(answer.getAnswer(), addHistoryQuestionVO.getAnswer());

        if(flag){
            redisTemplate.opsForValue().increment("score" + addHistoryQuestionVO.getUserId());// 设置成绩
        }

        // 返回的vo
        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setRight(flag);
        answerInfo.setAnswerExplain(answer.getAnswerExplain());
        answerInfo.setAnswer(answer.getAnswer());


        // 历史记录中查询
        HistoryQuestion historyQuestion1 = historyQuestionMapper.selectOne(wrapper);
        // 查询一下用户
        User user = userMapper.selectById(addHistoryQuestionVO.getUserId());


        // 更新用户信息
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", addHistoryQuestionVO.getUserId());

        if( !flag && historyQuestion1 == null){
            userUpdateWrapper.set("mistake", user.getMistake() + 1);
        }
        if( historyQuestion1 != null ){
            // 获取当前做过多少次
            answerInfo.setFrequency(historyQuestion1.getFrequency() + 1);
            answerInfo.setMistake(flag ? historyQuestion1.getMistake(): historyQuestion1.getMistake()  + 1);
            // 修改条件
            UpdateWrapper<HistoryQuestion> objectUpdateWrapper = new UpdateWrapper<>();
            objectUpdateWrapper.eq("id", historyQuestion1.getId());
            objectUpdateWrapper.set("frequency", historyQuestion1.getFrequency() + 1) ;
            // 判断当前是否答错了
            if(!flag){
                objectUpdateWrapper.set("mistake", historyQuestion1.getMistake() + 1) ;
            }

            int update = historyQuestionMapper.update(historyQuestion1, objectUpdateWrapper);
            if(update == 1){
                // 设置返回值
                responseVO.setData(answerInfo);
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
            historyQuestion.setType(addHistoryQuestionVO.getType());
            historyQuestion.setQuestionTitle(addHistoryQuestionVO.getQuestionTitle());

            // 添加返回信息
            answerInfo.setFrequency(1);
            answerInfo.setMistake(flag ? 0 : 1);

            // 新增的时候让做过的题+1
            userUpdateWrapper.set("quantity", user.getQuantity() + 1);
            // 判断当前是否答错了
            if(!flag){
                historyQuestion.setMistake(1);

            }
            // 插入到数据库
            int insert = historyQuestionMapper.insert(historyQuestion);
            if(insert == 1){
                // 设置返回值
                responseVO.setCode("200");
                responseVO.setData(answerInfo);
                responseVO.setMessage("保存成功");
            }else{
                // 设置返回值
                responseVO.setCode("-1");
                responseVO.setMessage("保存失败！");
            }
        }

        int update1 = userMapper.update(user, userUpdateWrapper);
        if(update1 != 1){
            // 设置返回值
            responseVO.setCode("-1");
            responseVO.setMessage("更新用户信息失败！");
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
