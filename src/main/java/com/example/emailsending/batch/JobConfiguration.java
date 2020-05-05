package com.example.emailsending.batch;

import com.example.emailsending.batch.EmailSendingTask;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configuration class for tasklets
 */
@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private InfoSettingTask infoSettingTask;

    @Autowired
    private EmailSendingTask emailSendingTask;

    @Bean
    public Step infoStep() {
        return stepBuilderFactory.get("step2")
                .tasklet(infoSettingTask)
                .build();
    }

    @Bean
    public Step emailStep() {
        return stepBuilderFactory.get("step1")
                .tasklet(emailSendingTask)
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("taskletsJob")
                .incrementer(new RunIdIncrementer())
                //building the flow builder
                .start(infoStep()).on("*").to(emailStep())
                .from(emailStep()).on("FAILED").fail()
                .from(emailStep()).on("*").end()
                .build()
                // the above 'build' is for flow builder, below is for jobBuilderFactory
                .build();
    }

}
