package com.pblgllgs.sb2batchflowexecution.repositories;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import com.pblgllgs.sb2batchflowexecution.entities.TransferPaymentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransferPaymentRepository extends CrudRepository<TransferPaymentEntity,String> {

    @Modifying
    @Transactional
    @Query("update TransferPaymentEntity tpe SET tpe.isProcessed =?1 where tpe.transactionId = ?2")
    void updateTransactionStatus(Boolean newValor, String transaction);

    @Modifying
    @Transactional
    @Query("update TransferPaymentEntity tpe SET tpe.isProcessed =?1, tpe.error =?2 where tpe.transactionId = ?3")
    void updateTransactionStatusError(Boolean newValor ,String errorString,String transaction);
}
