package org.javaboy.provider;

import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class HelloController {

    //get请求接口
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    //get请求接口
    @GetMapping("/hello2")
    public String hello2(String name) {
        System.out.println(new Date() +">>>" + name);
        return "hello:" + name;
    }

    //post请求接口
    //以key/value的形式传参
    @PostMapping("/user1")
    public User addUser1(User user) {
        return user;
    }
    //以JSON的形式传参,参数要加上@RequestBody注解
    @PostMapping("/user2")
    public User addUser2(@RequestBody User user) {
        return user;
    }

    //put请求接口
    //以key/value的形式传参
    @PutMapping("/updateUser1")
    public void updateUser1(User user) {
        System.out.println(user);
    }
    //以JSON的形式传参,参数要加上@RequestBody注解
    @PutMapping("/updateUser2")
    public void updateUser2(@RequestBody User user) {
        System.out.println(user);
    }

    //delete请求接口
    //以key/value的形式传参
    @DeleteMapping("/user1")
    public void delete1(Integer id) {
        System.out.println(id);
    }
    //参数放在路径中
    @DeleteMapping("/user2/{id}")
    public void delete2(@PathVariable Integer id) {
        System.out.println(id);
    }

}
