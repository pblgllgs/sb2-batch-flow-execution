package com.pblgllgs.sb2batchflowexecution.batch;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import com.pblgllgs.sb2batchflowexecution.entities.TransferPaymentEntity;
import com.pblgllgs.sb2batchflowexecution.repositories.TransferPaymentRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateAccountTasklet implements Tasklet {

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        boolean filterIsApproved = true;
        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();
        TransferPaymentEntity transferPaymentEntity = transferPaymentRepository.findById(transactionId).orElseThrow();
        if (Boolean.FALSE.equals(transferPaymentEntity.getIsEnabled())) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("message", "error, cuenta inactiva");
            filterIsApproved = false;
        }
        if (transferPaymentEntity.getAmountPaid() > transferPaymentEntity.getAvailableBalance()) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("message", "error, fondos insuficientes");
            filterIsApproved = false;
        }

        ExitStatus exitStatus = null;
        if (filterIsApproved) {
            exitStatus = new ExitStatus("VALID");
            stepContribution.setExitStatus(exitStatus);
        } else {
            exitStatus = new ExitStatus("INVALID");
            stepContribution.setExitStatus(exitStatus);
        }


        return RepeatStatus.FINISHED;
    }
}
