package com.lc.driving_school.service.historyTest;

import com.lc.driving_school.mapper.HistoryTestMapper;
import com.lc.driving_school.pojo.HistoryTest;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class HistoryTestService {
    private final HistoryTestMapper historyTestMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加考试记录
     * @params userId
     * @params userName
     */
    public ResponseVO addHistoryTest(String userId, String userName){
        ResponseVO responseVO = new ResponseVO();

        HistoryTest historyTest = new HistoryTest();
        historyTest.setUserId(userId);
        historyTest.setUserName(userName);

        // 获取redis中的分数
        Object o = redisTemplate.opsForValue().get("score" + userId);
        Object o1 = redisTemplate.opsForValue().get("timeout" + userId);

        historyTest.setFraction( o != null ? (Integer) o : 0);
        historyTest.setTime(o1 != null ? (String) o1 : "-1");

        int insert = historyTestMapper.insert(historyTest);
        if( insert == 1 ){
            responseVO.setCode("200");
            responseVO.setMessage("保存成功");
            responseVO.setData(true);
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("保存失败");
            responseVO.setData(false);
        }


        return responseVO;
    }
}
