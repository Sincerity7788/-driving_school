package com.lc.driving_school.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("com.lc.driving_school.mapper")
public class MyBatisPlus {
    /**
     * SQL执行性能分析插件
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor paginationInterceptor = new PerformanceInterceptor();
        // 参数单位为：ms，超过此处设置的ms则该SQL不会被执行
        paginationInterceptor.setMaxTime(6000);
        // 格式化SQL
        paginationInterceptor.setFormat(true);
        return paginationInterceptor;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
