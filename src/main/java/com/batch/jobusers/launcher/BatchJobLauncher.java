package com.batch.jobusers.launcher;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BatchJobLauncher - Job que dispara o SimpleJob configurado.
 */
@Component
public class BatchJobLauncher implements Job {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private org.springframework.batch.core.Job simpleJob;

    @Override
    public void execute(JobExecutionContext contextQuartz) throws JobExecutionException {
        try {
            jobLauncher.run(
                simpleJob,
                new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters()
            );
            System.out.println("✅ Batch Job executado com sucesso!");
        } catch (Exception e) {
            throw new JobExecutionException("Erro ao disparar o Batch Job", e);
        }
    }
}
