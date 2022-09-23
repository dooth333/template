package com.wenc.template.controller.interceptor;

import com.wenc.template.entity.LoginTicket;
import com.wenc.template.entity.User;
import com.wenc.template.service.impl.LoginTicketServiceImpl;
import com.wenc.template.service.impl.UserServiceImpl;
import com.wenc.template.utils.CookieUtil;
import com.wenc.template.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LoginTicketServiceImpl loginTicketService;

    @Autowired
    private HostHolder hostHolder;

    /***
     * 在Controller之前完成
     * 把cookie中的ticket凭证查出登录信息存入hostHolder(线程隔离)
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null){
            LoginTicket loginTicket = loginTicketService.findLoginTicketByTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {//不为空，状态有效，未超时
                //根据凭证查询用户
                User user = userService.getById(loginTicket.getUserId());
                //在本次请求持有用户（）（在多个线程进行隔离）(调用util类HostHolder)
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    /***
     * 调用Controller之后执行，在模板引擎之前
     * 在模板加载前把user从host取出，放入模板model中
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }
    }

    /***
     * 模板引擎执行之后执行
     * 清理host中user
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
