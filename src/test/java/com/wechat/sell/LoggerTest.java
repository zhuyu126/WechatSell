package com.wechat.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {


    @Test
    public void test1(){
        String name="wechat";
        String password="123456";
        log.debug("debug...");
        log.info("info...");
        log.info("name"+name+"password"+password);
        log.info("name:{},password{}",name,password);
        log.error("error...");
        log.warn("warn...");

    }
}
