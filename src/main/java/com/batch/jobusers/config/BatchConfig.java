package com.batch.jobusers.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Carrega a configuração do Spring Batch baseada em XML.
 */
@Configuration
@EnableBatchProcessing
@ImportResource({"classpath:batch/job-config.xml"}) 
public class BatchConfig {}

