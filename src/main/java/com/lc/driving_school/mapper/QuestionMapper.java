package com.lc.driving_school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.driving_school.pojo.Question;
import org.springframework.stereotype.Repository;

@Repository // 这个注解代表持久层
public interface QuestionMapper extends BaseMapper<Question> {
}
