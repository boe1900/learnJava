package com.boe;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Boe on 2016-09-10.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new com.alibaba.dubbo.container.Main().main(args);
    }
}
