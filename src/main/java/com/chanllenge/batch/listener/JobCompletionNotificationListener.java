package com.chanllenge.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.Date;

public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private long start;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        start = System.currentTimeMillis();
        LOGGER.info("Job with id {} is about to start at {}", jobExecution.getJobId(), new Date());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long end = System.currentTimeMillis();
        LOGGER.info("Job completed at {}", new Date());
        LOGGER.info("Job execution time in mills {}", (end - start));
        LOGGER.info("Job status {}", jobExecution.getStatus());
    }
}
