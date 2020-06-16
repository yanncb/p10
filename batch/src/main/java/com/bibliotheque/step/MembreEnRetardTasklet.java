package com.bibliotheque.step;

import com.bibliotheque.Service.BatchService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MembreEnRetardTasklet implements Tasklet {

    @Autowired
    BatchService batchService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        batchService.sendMaill();

        return RepeatStatus.FINISHED;
    }
}
