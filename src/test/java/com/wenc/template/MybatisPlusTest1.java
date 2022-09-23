package com.wenc.template;

import com.wenc.template.entity.User;
import com.wenc.template.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class MybatisPlusTest1 {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testSelectList(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

/*    @Test
    public void testInsert(){
        User user = new User();
        user.setAge(25);
        user.setEmail("zasd@qq.com");
        user.setName("张阿斯顿");
        int insert = userMapper.insert(user);
        System.out.println("insert:"+ insert);
        System.out.println("id:" + user.getId());
    }*/

    @Test
    public void testDelete(){

//        int delete = userMapper.deleteById(1565939006826328065L);
//        System.out.println("delete:" + delete);

//        Map<String,Object> map = new HashMap<>();
//        map.put("name","张三");
//        map.put("age",18);
//        int result = userMapper.deleteByMap(map);
//        System.out.println(result);

        List<Long> list = Arrays.asList(1L, 2L, 3L);
        int i = userMapper.deleteBatchIds(list);
        System.out.println(i);

    }

/*    @Test
    public void testSelectById(){
//        User user = userMapper.selectById(1L);
//        System.out.println(user);

//        List<Long> list1 = Arrays.asList(1L, 2L, 3L);
//        List<User> users = userMapper.selectBatchIds(list1);
//        System.out.println(users);

//        Map<String,Object> map = new HashMap<>();
//        map.put("name","Jack");
//        map.put("age",20);
//        List<User> users = userMapper.selectByMap(map);
//        users.forEach(System.out::println);

        Map<String, Object> map = userMapper.selectMapById(4L);
        System.out.println(map);
    }*/


/*    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(4L);
        user.setName("李四");
        user.setEmail("lisi@qq.com");
        int result = userMapper.updateById(user);
        System.out.println(result);
    }*/
}
