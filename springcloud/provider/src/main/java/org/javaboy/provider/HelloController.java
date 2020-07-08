package org.javaboy.provider;

import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

@RestController
public class HelloController implements IUserService {//实现接口后，访问地址的注解就不用写了

    //get请求接口
//    @GetMapping("/hello")
    @Override
    public String hello() {
        return "hello";
    }

    //get请求接口
//    @GetMapping("/hello2")
    @Override
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
//    @PostMapping("/user2")
    @Override
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
//    @DeleteMapping("/user2/{id}")
    @Override
    public void delete2(@PathVariable Integer id) {
        System.out.println(id);
    }

//    @GetMapping("/user3")
    @Override
    public void getUserByName(@RequestHeader String name) throws UnsupportedEncodingException {
        //调用时中文回转码，这里就要解码了
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }

}
