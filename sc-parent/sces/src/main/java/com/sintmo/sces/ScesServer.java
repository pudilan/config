package com.sintmo.sces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 * 
 * @author zhangfuchao
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class ScesServer {

	public static void main(String[] args) {
		SpringApplication.run(ScesServer.class, args);
	}

}
