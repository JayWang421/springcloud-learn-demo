package org.javaboy.provider;

import org.javaboy.commons.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class RegisterController {

    @PostMapping("register")
    public String register(User user) throws UnsupportedEncodingException {
        //重定向的地址一定要写成绝对路径，不要写成相对路径  ，否则调用时会出错
        return "redirect:http://provider/loginPage?name=" + URLEncoder.encode(user.getName(), "UTF-8");//重定向
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String name) {
        return "loginPage :" + name;
    }

}
