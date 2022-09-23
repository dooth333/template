package com.wenc.template;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wenc.template.entity.User;
import com.wenc.template.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        //查询用户名包含a，年龄20到30，邮箱不为null
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","李")//模糊查询，第一参数数据库字段名，第二个参数模糊查询字
                    .between("age",20,30)
                    .isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        System.out.println(list);
    }

    @Test
    public void test02(){
        //查询用户信息，按照年龄的的降序排列，若年龄相同，则按照id升序排列
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                .orderByAsc("id");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }


    @Test
    public void test03(){
        String username = "李";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            //isBlank判断某个字符是否不为空字符串，不为null，不为空白符
            queryWrapper.like("name",username);
        }
        if (ageBegin != null){
            queryWrapper.ge("age",ageBegin);
        }
        if (ageEnd != null){
            queryWrapper.le("age",ageEnd);
        }
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test04(){
        String username = "华";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //isBlank判断某个字符是否不为空字符串，不为null，不为空白符
            queryWrapper.like(StringUtils.isNotBlank(username),"name",username)
                        .ge(ageBegin != null,"age",ageBegin)
                        .le(ageEnd != null,"age",ageEnd);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

/*    @Test
    public void test05(){
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,username)
                .ge(ageBegin != null,User::getAge,ageBegin)
                .le(ageEnd != null,User::getAge,ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }*/

/*    @Test
    public void test06(){
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName,"a")
                .and(i->i.gt(User::getAge,20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName,"小黑").set(User::getEmail,"abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println(result);
    }*/
}
