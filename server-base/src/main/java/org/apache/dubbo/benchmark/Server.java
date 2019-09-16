package org.apache.dubbo.benchmark;

import com.youzan.aladdin.AladdinContainer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        AladdinContainer.run(args);
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            context.start();
            Thread.sleep(Integer.MAX_VALUE);
        }
    }

}
