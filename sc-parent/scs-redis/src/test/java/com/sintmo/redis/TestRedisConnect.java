package com.sintmo.redis;

import org.junit.Test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

public class TestRedisConnect {

    @Test
    public void ConnectToRedis() {
        // Syntax: redis://[password@]host[:port][/databaseNumber]
        RedisClient redisClient = RedisClient.create("redis://abab1456@192.168.56.140:6379/0");
        System.out.println("create a client");

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToRedisSSL() {
        // Syntax: rediss://[password@]host[:port][/databaseNumber]
        // Adopt the port to the stunnel port in front of your Redis instance
        RedisClient redisClient = RedisClient.create("rediss://password@localhost:6443/0");

        StatefulRedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis using SSL");

        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToRedisUsingRedisSentinel() {
        // Syntax:
        // redis-sentinel://[password@]host[:port][,host2[:port2]][/databaseNumber]#sentinelMasterId
        RedisClient redisClient = RedisClient.create("redis-sentinel://localhost:26379,localhost:26380/0#mymaster");

        StatefulRedisConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis using Redis Sentinel");

        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void ConnectToRedisCluster() {
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:7379");

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }

}
