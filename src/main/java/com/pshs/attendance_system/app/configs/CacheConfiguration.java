

package com.pshs.attendance_system.app.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
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
}