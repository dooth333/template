package com.wenc.template.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wenc.template.entity.LoginTicket;
import com.wenc.template.service.impl.LoginTicketServiceImpl;
import com.wenc.template.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wenc
 * @since 2022-09-05
 */
@Controller
public class LoginTicketController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LoginTicketServiceImpl loginTicketService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/quantam-lite/login1.html";
    }

    /***
     * 登录
     * @param username
     * @param password
     * @param remember
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(Model model ,String username, String password, boolean remember, HttpServletResponse response){

        int expiredSeconds = remember ? 3600 * 24 * 100 : 3600 * 12;
        Map<String, Object> map = loginTicketService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            LambdaQueryWrapper<LoginTicket> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LoginTicket::getTicket,map.get("ticket").toString());
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            if (map.containsKey("usernameMag")) {
                model.addAttribute("usernameMag", map.get("usernameMag").toString());
            } else {
                model.addAttribute("usernameMag", null);
            }
            if (map.containsKey("passwordMag")) {
                model.addAttribute("passwordMag", map.get("passwordMag").toString());
            } else {
                model.addAttribute("passwordMag", null);
            }
                return "/quantam-lite/login1.html";
        }
    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        loginTicketService.logout(ticket);
        return "/quantam-lite/login1.html";
    }

}

