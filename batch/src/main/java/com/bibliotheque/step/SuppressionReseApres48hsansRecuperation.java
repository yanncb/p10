package com.bibliotheque.step;

import com.bibliotheque.Service.BatchService;
import com.bibliotheque.models.Reservation;
import com.bibliotheque.proxies.ProxyBatchToBack;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SuppressionReseApres48hsansRecuperation implements Tasklet {

    @Autowired
    BatchService batchService;

    @Autowired
    ProxyBatchToBack proxyBatchToBack;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        List<Reservation> listeDeReservation = batchService.listeDesReservationAyantUnExemplaireDispoDepuisPlusDe48h();
        List<Reservation> listeTrieDeReservationASupprimer = new ArrayList<>();
        for (Reservation reservation : listeDeReservation) {
            if (reservation.getDateEnvoieMail().isAfter(reservation.getDateEnvoieMail().plusDays(2))){
                listeTrieDeReservationASupprimer.add(reservation);
            }
        }
        proxyBatchToBack.listeDeReservationASupprimer(listeTrieDeReservationASupprimer);

        return RepeatStatus.FINISHED;
    }
}
