package com.sintmo.redis.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.cluster.RedisClusterClient;

@Component
public class RedisClients {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClients.class);

    private Map<String, AbstractRedisClient> clients = new HashMap<String, AbstractRedisClient>();

    /**
     * 创建连接
     * 
     * @param type 1:单机 2:哨兵 3:集群
     * @param url
     * @return
     */
    public String createClient(int type, String url) {
        String id = null;
        if (type == 1 || type == 2) {
            id = UUID.randomUUID().toString();

            RedisClient redisClient = RedisClient.create(url);

            redisClient.connect();
            clients.put(id, redisClient);
        } else if (type == 3) {
            id = UUID.randomUUID().toString();

            RedisClusterClient redisClient = RedisClusterClient.create(url);
            redisClient.connect();
            clients.put(id, redisClient);
        } else {
            LOGGER.error("the type is undefined!");
        }
        return id;
    }

}
