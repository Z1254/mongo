package com.autohome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhangzhenning
 * @since 2020/1/7 9:33
 */

@MapperScan(basePackages = { "com.autohome.dao.mapper"})
@SpringBootApplication
@EnableAsync
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
}
