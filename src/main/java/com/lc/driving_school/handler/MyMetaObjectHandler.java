package com.lc.driving_school.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 修改创建时间
        this.setFieldValByName("createTime", new Date(), metaObject);
        // 修改更新时间
        this.setFieldValByName("updateTime", new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新的时候不用修改创建时间
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
