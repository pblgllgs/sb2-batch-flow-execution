package com.pblgllgs.sb2batchflowexecution.batch;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import com.pblgllgs.sb2batchflowexecution.repositories.TransferPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CancelTransactionTasklet implements Tasklet {

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();
        String message = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().getString("message");
        log.error("----- PROCESS TRANSACTION CANCEL: {}-----", transactionId);
        transferPaymentRepository.updateTransactionStatusError(true, message, transactionId);
        return RepeatStatus.FINISHED;
    }
}
