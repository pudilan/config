package com.sintmo.scsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务a
 * 
 * @author zhangfuchao
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ScsaServer {

	public static void main(String[] args) {
		SpringApplication.run(ScsaServer.class, args);
	}

}
