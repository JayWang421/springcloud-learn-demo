package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * 用类继承的方式实现请求
 */
public class HelloCommand extends HystrixCommand<String> {

    RestTemplate restTemplate;

    public HelloCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    /**
     * 请求失败调用的方法（容错/降级）
     * @return
     */
    @Override
    protected String getFallback() {
        return "error-extends:" + getExecutionException().getMessage();//获取执行的异常信息
    }
}
