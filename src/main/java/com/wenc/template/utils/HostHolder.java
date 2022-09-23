package com.wenc.template.utils;

import com.wenc.template.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    /***
     * 持有用户的信息，代替session对象
     * 用隔离线程存储信息
     * 这样可以防止多个人访问时多线程冲突问题
     */
    private ThreadLocal<User> users = new ThreadLocal<>();

    /***
     * 存入user信息
     * @param user
     */
    public void setUser(User user){
        users.set(user);
    }

    /***
     * 取出user信息
     * @return
     */
    public User getUser(){
        return users.get();
    }

    /***
     * 清理user信息
     */
    public void clear(){
        users.remove();
    }
}
