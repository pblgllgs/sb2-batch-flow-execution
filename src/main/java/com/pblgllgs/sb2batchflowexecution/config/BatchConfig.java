package com.pblgllgs.sb2batchflowexecution.config;

import com.pblgllgs.sb2batchflowexecution.batch.CancelTransactionTasklet;
import com.pblgllgs.sb2batchflowexecution.batch.ProcessPaymentTasklet;
import com.pblgllgs.sb2batchflowexecution.batch.SendNotificationTasklet;
import com.pblgllgs.sb2batchflowexecution.batch.ValidateAccountTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public ValidateAccountTasklet validateAccountTasklet(){
        return new ValidateAccountTasklet();
    }

    @Bean
    public ProcessPaymentTasklet processPaymentTasklet(){
        return new ProcessPaymentTasklet();
    }

    @Bean
    public SendNotificationTasklet sendNotificationTasklet(){
        return new SendNotificationTasklet();
    }

    @Bean
    public CancelTransactionTasklet cancelTransactionTasklet(){
        return new CancelTransactionTasklet();
    }

    @Bean
    @JobScope
    public Step validateAccountStep(){
        return stepBuilderFactory
                .get("validateAccountStep")
                .tasklet(validateAccountTasklet())
                .build();
    }

    @Bean
    public Step processPaymentStep(){
        return stepBuilderFactory
                .get("processPaymentStep")
                .tasklet(processPaymentTasklet())
                .build();
    }

    @Bean
    public Step sendNotificationStep(){
        return stepBuilderFactory
                .get("sendNotificationStep")
                .tasklet(sendNotificationTasklet())
                .build();
    }

    @Bean
    public Step cancelTransactionStep(){
        return stepBuilderFactory
                .get("cancelTransactionTasklet")
                .tasklet(cancelTransactionTasklet())
                .build();
    }

    @Bean
    public Job transactionPaymentJob() {
        return jobBuilderFactory.get("transactionPaymentJob")
                .start(validateAccountStep())
                .on("VALID").to(processPaymentStep())
                .next(sendNotificationStep())
                .from(validateAccountStep())
                .on("INVALID").to(cancelTransactionStep())
                .next(sendNotificationStep())
                .end().build();

    }
}
