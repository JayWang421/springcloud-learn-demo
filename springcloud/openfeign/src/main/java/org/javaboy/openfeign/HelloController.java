package org.javaboy.openfeign;

import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() throws UnsupportedEncodingException {
        String s = helloService.hello2("nihao");
        System.out.println(s);
        User user = new User();
        user.setId(12);
        user.setName("你好");
        User user1 = helloService.addUser2(user);
        System.out.println(user1);
        helloService.delete2(12);
        helloService.getUserByName(URLEncoder.encode("你好","UTF-8"));//调用时中文一定要转码
        return helloService.hello();
    }
}
