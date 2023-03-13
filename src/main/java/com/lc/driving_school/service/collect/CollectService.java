package com.lc.driving_school.service.collect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.driving_school.mapper.CollectMapper;
import com.lc.driving_school.pojo.Collect;
import com.lc.driving_school.vo.AddCollectVO;
import com.lc.driving_school.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CollectService {
    private final CollectMapper collectMapper;

    // 删除当前指定用户收藏的指定题
    public ResponseVO deleteUserCollect(String userId, String questionId){
        ResponseVO responseVO = new ResponseVO();
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id", userId);
        collectQueryWrapper.eq("question_id", questionId);
        // 删除
        int delete = collectMapper.delete(collectQueryWrapper);

        if(delete == 1){
            responseVO.setCode("200");
            responseVO.setMessage("删除收藏成功！");
            responseVO.setData(true);
        }else{
            responseVO.setCode("-1");
            responseVO.setMessage("删除收藏失败！");
            responseVO.setData(false);
        }

        return responseVO;
    }

    // 查询指定用户是否收藏过该题目
    public ResponseVO userHasCollect(String userId, String questionId){
        ResponseVO responseVO = new ResponseVO();

        try{
            // 定义请求信息
            QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
            collectQueryWrapper.eq("user_id", userId);
            collectQueryWrapper.eq("question_id", questionId);

            // 查询
            Collect collect = collectMapper.selectOne(collectQueryWrapper);
            if( collect == null ){
                responseVO.setCode("200");
                responseVO.setMessage("该题目未收藏！");
                responseVO.setData(false);
            }else{
                responseVO.setCode("200");
                responseVO.setMessage("该题目已收藏！");
                responseVO.setData(true);
            }

        }catch (Error e){
            responseVO.setCode("-1");
            responseVO.setMessage("数据库操作失败!");
            responseVO.setData(e);
        }



        return responseVO;
    }

    // 添加
    public ResponseVO addCollect(AddCollectVO addCollectVO){
        ResponseVO responseVO = this.userHasCollect(addCollectVO.getUserId(), addCollectVO.getQuestionId());
        // 判断当前是否已经添加过了
        if((boolean)(responseVO.getData())){
            responseVO.setCode("-1");
            responseVO.setMessage("已经关联过该题了!");
            responseVO.setData(false);
            return responseVO;
        }

        try{
            // 实例化添加类
            Collect collect = new Collect();
            collect.setQuestionId(addCollectVO.getQuestionId());
            collect.setUserId(addCollectVO.getUserId());
            collect.setQuestionTitle(addCollectVO.getQuestionTitle());

            // 添加到数据库
            int insert = collectMapper.insert(collect);
            if( insert == 1 ){
                responseVO.setCode("200");
                responseVO.setMessage("数据库保存成功");
                responseVO.setData(true);
            }else{
                responseVO.setCode("-1");
                responseVO.setMessage("数据库保存失败!");
                responseVO.setData(false);
            }

        }catch (Error e){
            responseVO.setCode("-1");
            responseVO.setMessage("数据库操作失败!");
            responseVO.setData(e);
        }




        return responseVO;
    }
}
