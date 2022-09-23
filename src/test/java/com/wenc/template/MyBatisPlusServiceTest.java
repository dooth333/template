package com.wenc.template;

import com.wenc.template.entity.User;
import com.wenc.template.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testGetCount(){
        int count = userService.count();
        System.out.println("总记录数:" + count);
    }

/*    @Test
    public void testInsertMore(){
        List<User>  list = new ArrayList<>();
        for (int i = 1; i <= 10 ; i++) {
            User user = new User();
            user.setName("李华"+ i);
            user.setAge(18+i);
            user.setEmail("lihua"+1+"@qq.com");
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println("是否添加成功:"+b);
    }*/


}
