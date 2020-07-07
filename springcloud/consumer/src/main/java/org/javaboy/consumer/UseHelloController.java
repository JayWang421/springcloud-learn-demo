package org.javaboy.consumer;

import org.javaboy.commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;

@RestController
public class UseHelloController {

    //手动负载均衡和用Java原生http请求
    @Autowired
    DiscoveryClient discoveryClient;
    int count = 0;
    @GetMapping("/hello1")
    public String hello1() {
        //springcloud 提供的获取服务的类，获取到的是集合（服务有可能会是集群部署）
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
//        ServiceInstance instance = list.get((count++) % list.size());//集群为两个时，手动做线性负载均衡
        ServiceInstance instance = list.get(0);
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        HttpURLConnection con = null;
        try {
            URL url = new URL(sb.toString());
            con = (HttpURLConnection) url.openConnection();
            if(con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String s = br.readLine();
                    br.close();
                    return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    //使用spring提供的RestTemplate来做http请求和使用@LoadBalanced注解来做负载均衡
    //要先注入RestTemplate（目前在启动类中注入了）
    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;
    @GetMapping("/hello2")
    public String hello2() {
        //springcloud 提供的获取服务的类，获取到的是集合（服务有可能会是集群部署）
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance instance = list.get((count++) % list.size());//集群为两个时，手动做线性负载均衡
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer();
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");
        String s = restTemplateOne.getForObject(sb.toString(), String.class);//手动做了负载均衡，一定要写具体的调用地址
        return s;
    }

    //使用spring提供的RestTemplate来做http请求和使用@LoadBalanced注解来做负载均衡
    //要先注入RestTemplate（目前在启动类中注入了）
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;
    @GetMapping("/hello3")
    public String hello3() {
        String s = restTemplate.getForObject("http://provider/hello", String.class);//使用@LoadBalanced注解来做负载均衡，一定要写模糊的调用地址（服务名和接口）
        return s;
    }

    @GetMapping("/hello4")
    public void hello4() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "小明");
        map.add("password", "123");
        map.add("id", 12);
        User user = restTemplate.postForObject("http://provider/user1", map, User.class);
        System.out.println(user);

        user.setId(15);
        user = restTemplate.postForObject("http://provider/user2", user, User.class);
        System.out.println(user);
    }

    @GetMapping("/hello5")
    public void hello5() throws UnsupportedEncodingException {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "nihao");
        map.add("password", "123");
        map.add("id", 12);
        URI uri = restTemplate.postForLocation("http://provider/register", map);//重定向（注册完之后要重定向）
        String s = restTemplate.getForObject(uri, String.class);
        System.out.println(s);
    }

    @GetMapping("/hello6")
    public void hello6() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "小明");
        map.add("password", "123");
        map.add("id", 12);
        restTemplate.put("http://provider/updateUser1", map);
        User user = new User();
        user.setId(15);
        user.setName("小明");
        user.setPassword("123");
        restTemplate.put("http://provider/updateUser2", user);
    }

    @GetMapping("/hello7")
    public void hello7() {
        restTemplate.delete("http://provider/user1?id={1}", 99);
        restTemplate.delete("http://provider/user2/{1}", 10);
    }

}
