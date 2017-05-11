package com.boe;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Boe on 2016-09-10.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:spring/applicationContext.xml" });
        context.start();
        System.out.println("启动成功");
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
