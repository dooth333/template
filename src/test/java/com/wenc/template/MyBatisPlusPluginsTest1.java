package com.wenc.template;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenc.template.entity.User;
import com.wenc.template.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusPluginsTest1 {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPage(){
        Page<User> page = new Page<>(1,3);

        Page<User> page1 = userMapper.selectPage(page, null);
        System.out.println(page1.getRecords());//获取查询到的内容
        System.out.println(page1.getPages());//获取总页数
        System.out.println(page1.getTotal());//获取总条数
        System.out.println(page1.hasNext());//判断是否有下一页返回boolean
        System.out.println(page1.hasPrevious());//判断是否有上一页返回boolean
        System.out.println(page1);
    }

/*    @Test
    public void testPageVo(){
        Page<User> page = new Page<>(1,4);
        Page<User> page1 = userMapper.selectPageVo(page, 20);
        System.out.println(page1.getRecords());//获取查询到的内容
        System.out.println(page1.getPages());//获取总页数
        System.out.println(page1.getTotal());//获取总条数
        System.out.println(page1.hasNext());//判断是否有下一页返回boolean
        System.out.println(page1.hasPrevious());//判断是否有上一页返回boolean
    }*/
}
