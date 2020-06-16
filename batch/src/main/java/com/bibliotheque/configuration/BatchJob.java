package com.bibliotheque.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
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
