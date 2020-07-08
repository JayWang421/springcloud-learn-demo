package org.javaboy.openfeign;

import org.javaboy.commons.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Component
@RequestMapping("/javaboy")//改变请求地址，防止重复
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "error1";
    }

    @Override
    public String hello2(String name) {
        return "error2";
    }

    @Override
    public User addUser2(User user) {
        return null;
    }

    @Override
    public void delete2(Integer id) {

    }

    @Override
    public void getUserByName(String name) throws UnsupportedEncodingException {

    }
}
