package com.lc.driving_school.service.historyTest;

import com.lc.driving_school.mapper.HistoryTestMapper;
import com.lc.driving_school.mapper.UserMapper;
import com.lc.driving_school.pojo.HistoryTest;
import com.lc.driving_school.pojo.User;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class HistoryTestService {
    private final HistoryTestMapper historyTestMapper;

    private final UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加考试记录
     * @params userId
     * @params userName
     * @param finish
     */
    public ResponseVO addHistoryTest(String userId, String userName, int finish){
        ResponseVO responseVO = new ResponseVO();

        HistoryTest historyTest = new HistoryTest();
        historyTest.setUserId(userId);
        historyTest.setUserName(userName);

        // 获取redis中的分数
        Object o = redisTemplate.opsForValue().get("score" + userId);
        Object o1 = redisTemplate.opsForValue().get("timeout" + userId);

        historyTest.setFraction( o != null ? (Integer) o : 0);
        historyTest.setFinish(finish);
        if( o1 != null  ){
            long l = System.currentTimeMillis() - Long.parseLong(o1.toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            historyTest.setTime(simpleDateFormat.format(l));
        }else{
            historyTest.setTime("-1");
        }


        int insert = historyTestMapper.insert(historyTest);
        if( insert == 1 ){
            // 判断当前用户的最高分是否需要修改
            judgeUserFraction(userId, o != null ? (Integer) o : 0);
            responseVO.setCode("200");
            responseVO.setMessage("保存成功");
            responseVO.setData(historyTest.getFraction());
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("保存失败");
            responseVO.setData(false);
        }


        return responseVO;
    }

    /**
     * 判断当前用户的最高分
     * @params userId
     * @param finish
     */
    public void judgeUserFraction(String userId, int finish){
        // 查询用户的最高分
        User user = userMapper.selectById(userId);
        if( user.getFraction() < finish ){
            user.setFraction(finish);
            // 更新
            int update = userMapper.updateById(user);
        }
    }
}
