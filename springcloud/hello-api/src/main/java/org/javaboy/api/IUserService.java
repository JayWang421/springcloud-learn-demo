package org.javaboy.api;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

public interface IUserService {
    @GetMapping("/hello")//绑定的服务提供的接口
    String hello();//这里的方法名随便起

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);//以key/value形式的一定要加上参数名

    @PostMapping("/user2")
    User addUser2(@RequestBody User user);//以json形式，如果也要以参数的形式，那就象上面那样加上参数名

    @DeleteMapping("/user2/{id}")
    void delete2(@PathVariable("id") Integer id);//以key/value形式的一定要加上参数名

    @GetMapping("/user3")//用 @RequestHeader 传递参数时，中文一定要转码
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;//以key/value形式的一定要加上参数名
}
