package com.wenc.template;

import com.wenc.template.entity.User;
import com.wenc.template.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class UserTest01 {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void addUserTest(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("wenc@gmail.com");
        Map<String, Object> register = userService.register(user);
        System.out.println(register);
    }



}
