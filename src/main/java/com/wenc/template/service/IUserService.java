package com.wenc.template.service;

import com.wenc.template.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenc
 * @since 2022-09-05
 */
public interface IUserService extends IService<User> {

    /***
     * 注册
     * @param user
     * @return
     */
    public Map<String, Object> register(User user);

}
