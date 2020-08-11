package com.bibliotheque.configuration;

import com.bibliotheque.step.MembreEnRetardTasklet;
import com.bibliotheque.step.SuppressionReseApres48hsansRecuperation;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchJob {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Bean
    @Scheduled(cron = "${microservice.config.cron.job}")
    protected Step customStep(StepBuilderFactory stepBuilders) {
        return stepBuilders
                .get("customStep")
                .tasklet(new MembreEnRetardTasklet())
                .tasklet(new SuppressionReseApres48hsansRecuperation())
                .build();
    }

    @Bean
    @Scheduled(cron = "${microservice.config.cron.job}")
    public Job customJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders) {
        return jobBuilders
                .get(String.valueOf(job))
                .start(customStep(stepBuilders))
                .build();
    }




    /**
     * Programmation de la relance des emprunts à 3h00 du matin tous les jours
     *
     * @throws Exception
     */
    @Scheduled(cron = "${microservice.config.cron.job}")
    public void lendingRevival() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("sendReminderJob", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);

    }
}
