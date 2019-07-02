package com.sintmo.redis;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;

public class TestRedisSentinel {

    public StatefulRedisConnection<String, String> getConnection() {
        // redis-sentinel://[password@]host[:port][,host2[:port2]][/databaseNumber]#sentinelMasterId
        RedisClient redisClient = RedisClient.create("redis-sentinel://abab1456@192.168.56.140:26379/0#mymaster");

        StatefulRedisConnection<String, String> connection = redisClient.connect();

        return connection;
    }

    @Test
    public void query() throws InterruptedException, ExecutionException {
        RedisClient redisClient = RedisClient.create("redis-sentinel://abab1456@192.168.56.140:26379/0#mymaster");
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        RedisCommands<String, String> syncCommands = connection.sync();
        System.out.println(syncCommands.set("test", "hahaha"));

        // string
        syncCommands.append("test", "aaaa");
        System.out.println(syncCommands.get("test"));

        // hash
        syncCommands.hset("hash", "1", "1");
        syncCommands.hset("hash", "1", "2");
        System.out.println(syncCommands.hgetall("hash"));

        // list
        syncCommands.lpush("list", "1");
        syncCommands.lpush("list", "2");
        System.out.println(syncCommands.lrange("list", 0, -1));

        // set
        syncCommands.sadd("set", "1", "2", "2");
        System.out.println(syncCommands.smembers("set"));

        // sorted set
        syncCommands.zadd("sortedSet", 1, "1");
        syncCommands.zadd("sortedSet", 2, "2");
        List<ScoredValue<String>> sortedSet = syncCommands.zrangeWithScores("sortedSet", 0, -1);
        System.out.println(sortedSet);
        for (ScoredValue<String> value : sortedSet) {
            System.out.println(value.getValue() + "-----" + value.getScore());
        }

        // RedisAsyncCommands<String, String> asyncCommands = connection.async();
        // RedisFuture<String> result = asyncCommands.set("test", "wow wow wow");
        // while (!result.isDone()) {
        // System.out.println("--------------------");
        // System.out.println(result.get());
        // }
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void bytes() {
        RedisClient redisClient = RedisClient.create("redis-sentinel://abab1456@192.168.56.140:26379/0#mymaster");
        StatefulRedisConnection<byte[], byte[]> connection = redisClient.connect(new ByteArrayCodec());

        RedisCommands<byte[], byte[]> syncCommands = connection.sync();

        syncCommands.set("testbytes".getBytes(), "testbytes".getBytes());

        List<byte[]> keys = syncCommands.keys("*".getBytes());

        System.out.println(keys.size());
        System.out.println(keys);

        for (byte[] key : keys) {
            System.out.println(new String(key));
        }

        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void keys() {
        RedisClient redisClient = RedisClient.create("redis-sentinel://abab1456@192.168.56.140:26379/0#mymaster");
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        RedisCommands<String, String> syncCommands = connection.sync();

        List<String> keys = syncCommands.keys("*");
        System.out.println(keys);

        for (String key : keys) {
            System.out.println(syncCommands.type(key));
        }

        connection.close();
        redisClient.shutdown();
    }

}
