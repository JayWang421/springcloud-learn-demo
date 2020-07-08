package org.javaboy.openfeign;

import org.javaboy.api.IUserService;
import org.javaboy.commons.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//绑定的服务
//@FeignClient(value = "provider", fallback = HelloServiceFallback.class)
@FeignClient(value = "provider", fallbackFactory = HelloServiceFallbackFactory.class)//通过自定义FallbackFactory来进行服务降级
public interface HelloService extends IUserService {//继承接口后，下面的方法就可省略了

//    @GetMapping("/hello")//绑定的服务提供的接口
//    String hello();//这里的方法名随便起
//
//    @GetMapping("/hello2")
//    String hello2(@RequestParam("name") String name);//以key/value形式的一定要加上参数名
//
//    @PostMapping("/user2")
//    User addUser2(@RequestBody User user);//以json形式，如果也要以参数的形式，那就象上面那样加上参数名
//
//    @DeleteMapping("/user2/{id}")
//    void deleteUserById(@PathVariable("id") Integer id);//以key/value形式的一定要加上参数名
//
//    @GetMapping("/user3")//用 @RequestHeader 传递参数时，中文一定要转码
//    void getUserByName(@RequestHeader("name") String name);//以key/value形式的一定要加上参数名
}
