package com.sintmo.sccs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SccsServer {

    public static void main(String[] args) {
        SpringApplication.run(SccsServer.class, args);
    }

}
