

package com.pshs.attendance_system.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

	@Bean
	public Caffeine<Object, Object> caffeineManager() {
		return Caffeine.newBuilder()
			.initialCapacity(100)
			.maximumSize(5000)
			.expireAfterWrite(1, TimeUnit.MINUTES)
			.recordStats();
	}

	@Bean
	public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}
}