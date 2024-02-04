package com.pblgllgs.sb2batchflowexecution.batch;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


@Slf4j
public class SendNotificationTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();
        log.info("++++ NOTIFICATION SENT WITH ID: {}", transactionId);


        return RepeatStatus.FINISHED;
    }
}
