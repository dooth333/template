package com.wenc.template.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    //配置MybatisPlus插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //配置MybatisPlus插件的类
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//添加插件类型:分页插件，并且括号中添加数据库类型，mysql
        return interceptor;
    }

}
