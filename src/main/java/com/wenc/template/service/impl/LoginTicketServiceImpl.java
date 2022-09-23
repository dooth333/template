package com.wenc.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wenc.template.entity.LoginTicket;
import com.wenc.template.entity.User;
import com.wenc.template.mapper.LoginTicketMapper;
import com.wenc.template.mapper.UserMapper;
import com.wenc.template.service.ILoginTicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenc.template.utils.TemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenc
 * @since 2022-09-05
 */
@Service
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketMapper, LoginTicket> implements ILoginTicketService {

    private TemplateUtil templateUtil;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    /***
     * 登录
     * @param username 账号
     * @param password 密码
     * @param expiredSeconds 登陆凭证过期时间
     * @return
     */
    public Map<String,Object> login(String username, String password, int expiredSeconds){
        Map<String,Object> map = new HashMap<>();
        //空值验证
        if (StringUtils.isBlank(username)) {
            map.put("usernameMag", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMag", "密码不能为空！");
            return map;
        }
        //验证账号是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            map.put("usernameMag", "账号不存在！");
            return map;
        }
        //验证账号是否状态
        if (user.getStatus() == 0) {
            map.put("usernameMag", "账号未激活");
            return map;
        }
        //验证密码
        password = templateUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMag", "密码错误！");
            return map;
        }
        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(templateUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000L));//System.currentTimeMillis()转换成毫秒
        loginTicketMapper.insert(loginTicket);
        map.put("ticket",loginTicket.getTicket());
        return map;
    }


    /***
     * 退出登录
     * @param ticket 登录凭证
     */
    public void logout(String ticket){
        LambdaUpdateWrapper<LoginTicket> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LoginTicket::getTicket,ticket);
        updateWrapper.set(LoginTicket::getStatus,1);
        loginTicketMapper.update(null,updateWrapper);
    }

    /***
     * 通过ticket查询登陆凭证
     * @param ticket
     * @return
     */
    public LoginTicket findLoginTicketByTicket(String ticket){

        LambdaQueryWrapper<LoginTicket> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginTicket::getTicket,ticket);
        LoginTicket loginTicket = loginTicketMapper.selectOne(queryWrapper);
        return loginTicket;
    }


}
