package com.wenc.template.controller;


import com.wenc.template.entity.User;
import com.wenc.template.service.impl.UserServiceImpl;
import com.wenc.template.utils.GithubUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GithubUploader githubUploader;


    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/quantam-lite/register1.html";
    }

    @RequestMapping(path = "/index", method = {RequestMethod.GET,RequestMethod.POST})
    public String getIndexPage(){
        return "/index.html";
    }

    @RequestMapping(path = "/setting", method = {RequestMethod.GET,RequestMethod.POST})
    public String getSettingPage(){
        return "/quantam-lite/setting.html";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()){
            return "/quantam-lite/login1.html";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/quantam-lite/register1.html";
        }
    }



    @RequestMapping(path = ("/upload"),method = RequestMethod.POST)
    public String upload (@RequestParam("file") MultipartFile multipartFile,Model model) throws IOException {
        if (multipartFile  == null){
            model.addAttribute("error","您还没有选择图片,请选择图片");
            return "/quantam-lite/setting.html";
        }
        Map<String,Object> uploadMap = githubUploader.upload(multipartFile);
        String  error = (String) uploadMap.get("error");
        if (error == null || error.isEmpty()){
            model.addAttribute("error",error);
            return "/quantam-lite/setting.html";
        }
        String upload = (String) uploadMap.get("newUrl");
        if (upload == null||upload.isEmpty()){
            model.addAttribute("error","获取头像url为空，请通知管理员检查");
        }
        Map<String, Object> map = userService.upload(upload);
        if (map != null){
            model.addAttribute("userError",map.get("userError"));
        }
        return "/quantam-lite/setting.html";
    }


}

