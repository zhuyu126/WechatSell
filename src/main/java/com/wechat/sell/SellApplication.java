package com.wechat.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableCaching
//@MapperScan(basePackages = "com.wechat.sell.daomybatisgenertor")//使用mybatis时需要添加扫描路径
public class SellApplication {

    public static void main(String[] args) {
        SpringApplication.run( SellApplication.class, args );
    }

}
