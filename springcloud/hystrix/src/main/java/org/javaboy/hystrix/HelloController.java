package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public void hello2() {
        //一个Command实例只能执行一次
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate);
        String s = helloCommand.execute();//直接执行
        System.out.println(s);
        try {
            //一个Command实例只能执行一次，想要再次执行必须在new一个
            HelloCommand helloCommand1 = new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy")), restTemplate);
            Future<String> queue = helloCommand1.queue();//先入队，后执行
            String s1 = queue.get();
            System.out.println(s1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hello3")
    public void hello3() {
        Future<String> hello2 = helloService.hello2();
        String s = null;
        try {
            s = hello2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    @GetMapping("/hello4")
    public void hello4() {
        HystrixRequestContext hrc = HystrixRequestContext.initializeContext();//缓存在此处开启
        String s = helloService.hello3("javaboy");
        helloService.deleteUserByName("javaboy");//删除缓存
        s = helloService.hello3("javaboy");
        hrc.close();//缓存在此处结束
    }

    @Autowired
    UserService userService;

    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext hrc = HystrixRequestContext.initializeContext();//缓存在此处开启
        Future<User> future1 = userService.getUserbyId(99);
        Future<User> future2 = userService.getUserbyId(98);
        Future<User> future3 = userService.getUserbyId(97);
        User user1 = future1.get();
        User user2 = future2.get();
        User user3 = future3.get();
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        Thread.sleep(2000);
        Future<User> future4 = userService.getUserbyId(96);
        User user4 = future4.get();
        System.out.println(user4);
        hrc.close();//缓存在此处结束
    }
}
