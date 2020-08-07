package com.bibliotheque.configuration;

import com.bibliotheque.step.MembreEnRetardTasklet;
import com.bibliotheque.step.SuppressionReseApres48hsansRecuperation;
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

    public final SuppressionReseApres48hsansRecuperation suppressionReseApres48hsansRecuperation;



    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MembreEnRetardTasklet membreEnRetardTasklet, SuppressionReseApres48hsansRecuperation suppressionReseApres48hsansRecuperation) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.membreEnRetardTasklet = membreEnRetardTasklet;
        this.suppressionReseApres48hsansRecuperation = suppressionReseApres48hsansRecuperation;
    }

    @Bean
    public Job sendReminderJob(){
        return jobBuilderFactory.get("sendReminderJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

    @Bean
    public Step stepOne(){return stepBuilderFactory.get("stepOne").tasklet(membreEnRetardTasklet).build();}

    @Bean
    public Step stepTwo(){return stepBuilderFactory.get("stepTwo").tasklet(suppressionReseApres48hsansRecuperation).build();}

}
