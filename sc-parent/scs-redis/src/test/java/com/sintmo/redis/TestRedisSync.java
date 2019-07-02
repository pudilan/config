package com.sintmo.redis;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

public class TestRedisSync {

    @Test
    public void syncOfUser() {
        // 集群
        RedisClusterClient redisClusterClient = RedisClusterClient.create(
                "redis://10.14.122.63:7379,10.14.122.63:7380,10.14.122.63:7381,10.14.122.63:8379,10.14.122.63:8380,10.14.122.63:8381");
        StatefulRedisClusterConnection<String, String> clusterConnection = redisClusterClient.connect();
        System.out.println("Connected to Redis");
        RedisAdvancedClusterCommands<String, String> clusterCommands = clusterConnection.sync();

        // 哨兵
        RedisClient redisClient = RedisClient.create("redis-sentinel://abab1456@192.168.56.140:26379/0#mymaster");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        List<String> keys = clusterCommands.keys("{OFUSER}:*");
        Map<String, String> map;
        for (String key : keys) {
            System.out.println(clusterCommands.type(key));
            if ("hash".equals(clusterCommands.type(key))) {
                map = clusterCommands.hgetall(key);
                String userName = map.get("USERNAME");
                if (null != userName) {
                    key = key.replace("{", "");
                    key = key.replace("}", "");
                    syncCommands.hmset(key, map);
                }
            } else if ("set".equals(clusterCommands.type(key))) {
                String newKey = key.replace("{", "");
                newKey = newKey.replace("}", "");
                syncCommands.sadd(newKey, clusterCommands.smembers(key).toArray(new String[] {}));
            }
        }

        clusterConnection.close();
        connection.close();
    }

}
