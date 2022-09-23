package com.wenc.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wenc.template.entity.User;
import com.wenc.template.mapper.UserMapper;
import com.wenc.template.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenc.template.utils.HostHolder;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private TemplateUtil templateUtil;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;


    /***
     * 注册
     * @param user
     * @return
     */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        //空值处理
        if (user == null) {
            try {
                throw new IllegalAccessException("参数不能为空");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        //验证账号
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getUsername,user.getUsername());
        User selectByName = userMapper.selectOne(queryWrapper1);
        if (selectByName != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }
        //验证邮箱
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getEmail,user.getEmail());
        User selectByEmail = userMapper.selectOne(queryWrapper2);
        if (selectByEmail != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }

        //注册用户
        user.setSalt(templateUtil.generateUUID().substring(0, 5));
        user.setPassword(templateUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(templateUtil.generateUUID());
        user.setHeaderUrl("https://cdn.jsdelivr.net/gh/dooth333/note_image/img220906153911.png");
        user.setCreateTime(new Date());
        userMapper.insert(user);

/*        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domian + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活wenc账户", content);*/
        return map;
    }


    public Map<String,Object> upload(String url){
        Map<String,Object> map = new HashMap<>();
        User user = hostHolder.getUser();
        if (user == null){
            map.put("userError","没有登陆,请先登录");
            return map;
        }
        if (url == null || url.isEmpty()){
            map.put("userError","头像网址为空");
            return map;
        }
        user.setHeaderUrl(url);
        userMapper.updateById(user);
        return null;
    }




}
