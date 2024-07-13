package com.chanmul;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.chanmul.config.CustomJobLauncher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ExampleBatchApplication {
	private final JobLauncher jobLauncher;

	@Bean
	@Primary
	public JobLauncher customJobLauncher() {
		return new CustomJobLauncher(jobLauncher);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleBatchApplication.class, args);
		System.exit(SpringApplication.exit(applicationContext));
	}
}