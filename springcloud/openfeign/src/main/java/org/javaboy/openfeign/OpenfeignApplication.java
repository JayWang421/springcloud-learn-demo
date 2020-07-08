package org.javaboy.openfeign;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OpenfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeignApplication.class, args);
    }

    //请求日志级别（还要在application.propertise中开启）
    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

}
