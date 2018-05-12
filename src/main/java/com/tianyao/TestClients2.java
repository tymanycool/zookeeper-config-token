package com.tianyao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//@ContextConfiguration("classpath:applicationContext.xml")
public class TestClients2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ioc.start();

        ZkGetTokenClient zkGetTokenClient0 = (ZkGetTokenClient) ioc.getBean("zkGetTokenClient");
        ZkGetTokenClient zkGetTokenClient1 = (ZkGetTokenClient) ioc.getBean("zkGetTokenClient");
        ZkGetTokenClient zkGetTokenClient2 = (ZkGetTokenClient) ioc.getBean("zkGetTokenClient");
        ZkGetTokenClient zkGetTokenClient3 = (ZkGetTokenClient) ioc.getBean("zkGetTokenClient");
        new Thread(zkGetTokenClient0).start();
        new Thread(zkGetTokenClient1).start();
        new Thread(zkGetTokenClient2).start();
        new Thread(zkGetTokenClient3).start();

    }
}
