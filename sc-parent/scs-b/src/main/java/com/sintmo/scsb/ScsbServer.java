package com.sintmo.scsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务b
 * 
 * @author zhangfuchao
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ScsbServer {

	public static void main(String[] args) {
		SpringApplication.run(ScsbServer.class, args);
	}

}