package com.batch.jobusers.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch")
public class JobRunnerController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job simpleJob;

    @PostMapping("/start")
    public String startJob() throws Exception {
        jobLauncher.run(
            simpleJob,
            new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters()
        );
        return "Batch Job Disparado!";
    }
}
