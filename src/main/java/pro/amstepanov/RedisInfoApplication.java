
package pro.amstepanov;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class RedisInfoApplication{

	public static void main(String[] args) {
		SpringApplication.run(RedisInfoApplication.class, args);
	}

	/* пул для @Async; + @EnableAsync in config */
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("BKS-");
		executor.initialize();
		return executor;
	}
}