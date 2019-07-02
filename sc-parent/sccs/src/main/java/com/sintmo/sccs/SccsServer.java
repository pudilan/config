package com.sintmo.sccs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 * 
 * @author zhangfuchao
 *
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class SccsServer {

	public static void main(String[] args) {
		SpringApplication.run(SccsServer.class, args);
	}

}
