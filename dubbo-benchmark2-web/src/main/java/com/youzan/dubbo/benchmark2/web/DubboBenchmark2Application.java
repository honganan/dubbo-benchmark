package com.youzan.dubbo.benchmark2.web;

import com.youzan.boot.YouZanBootApplication;
import com.youzan.aladdin.AladdinContainer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = {"com.youzan.dubbo.benchmark2"})
@ImportResource({"classpath*:spring/applicationContext.xml"})
public class DubboBenchmark2Application {

	public static void main(String[] args) {
		AladdinContainer.run(args);
		YouZanBootApplication.run(DubboBenchmark2Application.class, args);
	}
}
