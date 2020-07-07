package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 在这个方法中发起远程调用，调用provider中的hello接口
     *
     * 但是，这个调用可能会失败
     *
     * 在这个方法上添加@HystrixCommand注解，配置fallbackMethod属性，这个属性表示调用失败后临时替代的方法
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    public String hello() {
        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/hello",String.class);
    }

    /**
     * 通过注解实现异步调用
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello",String.class);
            }
        };
    }

    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult//这个注解表示该方法的请求结果会被缓存下来，默认情况下缓存的key是方法的参数，缓存的value是请求的返回值
                //如果参数有多个，缓存的key就是多个key组合起来
                //如果参数有多个，但又想使用其中一个参数作为key，只需要在参数前面加上 @CacheKey 注解即可
    public String hello3(String name) {
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class, name);
    }

    @HystrixCommand(fallbackMethod = "error2")
    //在做数据缓存时，如果一个数据删除了，不仅要删除数据库中的数据，还希望也把缓存中的也删除了，这时就可以用 @CacheRemove注解了
    //@CacheRemove 注解使用时，必须指定 commandKey 属性，其值就是缓存方法的名字
    @CacheRemove(commandKey = "hello3")
    public String deleteUserByName(String name) {
        return name;
    }

    public String error2(String name) {
        return "error:" + name;
    }

    /**
     * 这个方法的名字要和fallbackMethod的一致
     * 方法返回值也要和对应的方法一致
     * @Param Throwable 程序抛出的异常导致的服务降级。可加该参数，降级并查看异常信息
     * @return
     */
    public String error(Throwable t) {
        return "error:" + t.getMessage();
    }
}
