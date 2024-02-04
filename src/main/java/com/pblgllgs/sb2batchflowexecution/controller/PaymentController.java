package com.pblgllgs.sb2batchflowexecution.controller;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import com.pblgllgs.sb2batchflowexecution.controller.request.TransferPaymentDTO;
import com.pblgllgs.sb2batchflowexecution.entities.TransferPaymentEntity;
import com.pblgllgs.sb2batchflowexecution.repositories.TransferPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
@Slf4j
public class PaymentController {

    private final JobLauncher jobLauncher;
    private final Job job;

    private final TransferPaymentRepository transferPaymentRepository;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferPayment(@RequestBody TransferPaymentDTO dto) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info(dto.toString());
        String uuid = UUID.randomUUID().toString();
        TransferPaymentEntity transferPaymentEntity = TransferPaymentEntity.builder()
                .transactionId(uuid)
                .availableBalance(dto.getAvailableBalance())
                .amountPaid(dto.getAmountPaid())
                .isEnabled(dto.getIsEnabled())
                .isProcessed(false)
                .build();
        transferPaymentRepository.save(transferPaymentEntity);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id",UUID.randomUUID().toString())
                .addString("transactionId",uuid)
                .toJobParameters();
        jobLauncher.run(job,jobParameters);
        Map<String,Object> httpResponse = new HashMap<>();
        httpResponse.put("TransactionId",uuid);
        httpResponse.put("Message","Transaction received");
        return ResponseEntity.ok(httpResponse);

    }

}
