package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    //使用注解的方式实现请求合并,属性有合并请求的方法，合并的配置（延迟时间等）等
    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {@HystrixProperty(name="timerDelayInMilliseconds", value = "200")})
    public Future<User> getUserbyId(Integer id) {
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids) {
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }

    //还可使用类继承的方式实现请求合并（比较麻烦）的话
}
