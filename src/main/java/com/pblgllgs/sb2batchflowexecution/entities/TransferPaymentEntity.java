package com.pblgllgs.sb2batchflowexecution.entities;
/*
 *
 * @author pblgl
 * Created on 04-02-2024
 *
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transfer_payment_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferPaymentEntity {

    @Id
    @Column(nullable = false)
    private String transactionId;
    @Column(nullable = false, name = "available_balance")
    private Double availableBalance;
    @Column(nullable = false, name = "amount_paid")
    private Double amountPaid;
    @Column(nullable = false, name = "is_enabled")
    private Boolean isEnabled;
    @Column(nullable = false, name = "is_processed")
    private Boolean isProcessed;
    private String error;
}
