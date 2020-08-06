package com.keshorecluster.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfiguration {

	@Bean(value = "myExecutor")
	public ThreadPoolTaskExecutor myExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("myExecutor");
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(15);
		executor.initialize();
		return executor;
	}
}
