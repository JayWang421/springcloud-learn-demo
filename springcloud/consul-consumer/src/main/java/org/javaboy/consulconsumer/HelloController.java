package org.javaboy.consulconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public void hello() {
        ServiceInstance instance = loadBalancerClient.choose("consul-prodiver");
        System.out.println("服务地址：" + instance.getUri());
        System.out.println("服务名称：" + instance.getServiceId());
        String s = restTemplate.getForObject(instance.getUri() + "/hello", String.class);
        System.out.println(s);
    }
}
