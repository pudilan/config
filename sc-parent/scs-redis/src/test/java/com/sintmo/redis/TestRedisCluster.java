package com.sintmo.redis;

import java.util.List;

import org.junit.Test;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

public class TestRedisCluster {

	@Test
	public void ConnectToRedisCluster() {
		// Syntax: redis://[password@]host[:port]
		RedisClusterClient redisClient = RedisClusterClient.create(
				"redis://10.14.122.63:7379,10.14.122.63:7380,10.14.122.63:7381,10.14.122.63:8379,10.14.122.63:8380,10.14.122.63:8381");

		StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

		System.out.println("Connected to Redis");

		RedisAdvancedClusterCommands<String, String> clusterCommands = connection.sync();

		List<String> keys = clusterCommands.keys("cherry_cache*");

		System.out.println(keys.size());
		System.out.println(keys);

		for (String key : keys) {
			System.out.println(clusterCommands.type(key));
		}
		connection.close();
		redisClient.shutdown();
	}

	@Test
	public void getValue() {
		String key = "cherry_cache:appinfo_mainfest:064145119_Android_all_1.0_289";

		// Syntax: redis://[password@]host[:port]
		RedisClusterClient redisClient = RedisClusterClient.create(
				"redis://10.14.122.63:7379,10.14.122.63:7380,10.14.122.63:7381,10.14.122.63:8379,10.14.122.63:8380,10.14.122.63:8381");
		StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
		RedisAdvancedClusterCommands<String, String> clusterCommands = connection.sync();

		String value = clusterCommands.get(key);

		String json = value.substring(value.indexOf("{"));
		System.out.println(json);

	}

}
