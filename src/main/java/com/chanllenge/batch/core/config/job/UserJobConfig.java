package com.chanllenge.batch.core.config.job;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.chanllenge.batch.core.config.listerner.JobCompletionNotificationListener;
import com.chanllenge.batch.core.config.listerner.UserSkipListener;
import com.chanllenge.batch.domain.entity.user.User;
import com.chanllenge.batch.processor.UserProcessor;

@Configuration
@RequiredArgsConstructor
public class UserJobConfig {
    private static final int CHUNK_SIZE = 1000; // Estratégia de maximizar throughput
    private static final int MAX_SKIP_LIMIT = 100; // Tolerância a falhas pontuais
    private static final int MAX_RETRY_LIMIT = 2; // Retentativas automáticas

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public UserProcessor userProcessor() {
        return new UserProcessor();
    }

    @Bean
    public JpaItemWriter<User> userWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(this.entityManagerFactory);
        return writer;
    }

    @Bean
    @StepScope
    public JsonItemReader<User> userReader(@Value("#{jobParameters['filePath']}") String path) {
        return new JsonItemReaderBuilder<User>()
                .name("userReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(User.class))
                .resource(new FileSystemResource(path))
                .maxItemCount(100_000)
                .build();
    }

    @Bean
    public UserSkipListener userSkipListener() {
        return new UserSkipListener();
    }

    @Bean
    public Step userStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("userStep", jobRepository)
                .<User, User>chunk(CHUNK_SIZE, transactionManager)
                .reader(userReader(null))
                .processor(userProcessor())
                .writer(userWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(MAX_SKIP_LIMIT)
                .retry(Exception.class)
                .retryLimit(MAX_RETRY_LIMIT)
                .listener(userSkipListener())
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step userStep) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(userStep)
                .listener(new JobCompletionNotificationListener())
                .build();
    }
}
