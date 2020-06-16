package com.bibliotheque.configuration;

import com.bibliotheque.step.MembreEnRetardTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;

    public final MembreEnRetardTasklet membreEnRetardTasklet;



    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MembreEnRetardTasklet membreEnRetardTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.membreEnRetardTasklet = membreEnRetardTasklet;
    }

    @Bean
    public Job sendReminderJob(){
        return jobBuilderFactory.get("sendReminderJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne()).build();
    }

    @Bean
    public Step stepOne(){return stepBuilderFactory.get("stepOne").tasklet(membreEnRetardTasklet).build();}
}
