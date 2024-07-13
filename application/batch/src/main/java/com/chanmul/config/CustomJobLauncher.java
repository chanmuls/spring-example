package com.chanmul.config;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomJobLauncher implements JobLauncher {
	private final JobLauncher jobLauncher;

	@Override
	public JobExecution run(Job job, JobParameters jobParameters) throws
			JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParametersBuilder builder = new JobParametersBuilder(jobParameters)
				.addLocalDateTime("currentTime", LocalDateTime.now());

		JobParameters builderJobParameters = builder.toJobParameters();

		log.info("builderJobParameters: {}", builderJobParameters);

		return jobLauncher.run(job, builderJobParameters);
	}
}
