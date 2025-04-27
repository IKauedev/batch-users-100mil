package com.chanllenge.batch.job;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.chanllenge.batch.entity.customer.Customer;
import com.chanllenge.batch.listener.JobCompletionNotificationListener;
import com.chanllenge.batch.processor.CustomerProcessor;

@Configuration
public class CustomerJobConfig {
	@Autowired
    private EntityManagerFactory entityManagerFactoryProvider;
   
    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    public JpaItemWriter<Customer> writer() {
        JpaItemWriter<Customer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(this.entityManagerFactoryProvider);
        return writer;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> reader(@Value("#{jobParameters['filePath']}") String path) {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerReader")
                .resource(new FileSystemResource(path))
                .linesToSkip(1)  // skip header row
                .delimited().delimiter(",")
                .names(
                		"index",
                		"customerId",
                		"firstName",
                		"lastName",
                		"company",
                		"city",
                		"country",
                		"phone1",
                		"phone2",
                		"email",
                		"subscriptionDate",
                		"website"
                )
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(reader(null))
                .writer(writer())
                .processor(customerProcessor())
                .build();
    }

    @Bean
    public Job importCustomerJob(JobRepository jobRepository, Step step1)  {
        return new JobBuilder("importCustomerJob", jobRepository)
                .start(step1)
                .listener(new JobCompletionNotificationListener())
                .build();
    }
}
