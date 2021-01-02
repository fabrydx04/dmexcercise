package ar.com.fabricio.alan.garcia.feedsexercise.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class ExecutorServiceConfig {
	@Bean("fixedThreadPool")
	public ThreadPoolExecutor fixedThreadPool() {
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	}

}
