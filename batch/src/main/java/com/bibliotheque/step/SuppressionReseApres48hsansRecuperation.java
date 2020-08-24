package com.bibliotheque.step;

import com.bibliotheque.Service.BatchService;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.proxies.ProxyBatchToBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuppressionReseApres48hsansRecuperation implements Tasklet, StepExecutionListener {

    @Autowired
    BatchService batchService;

    @Autowired
    ProxyBatchToBack proxyBatchToBack;

    private final Logger logger = LoggerFactory.getLogger(SuppressionReseApres48hsansRecuperation.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Custom step initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {
        batchService.listeDesReservationAyantUnExemplaireDispoDepuisPlusDe48h();
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Custom step ended.");
        return ExitStatus.COMPLETED;
    }
}

