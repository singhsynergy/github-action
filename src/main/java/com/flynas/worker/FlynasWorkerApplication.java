package com.flynas.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.flynas.worker.*", "com.common.*" })
public class FlynasWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlynasWorkerApplication.class, args);
	}

}
