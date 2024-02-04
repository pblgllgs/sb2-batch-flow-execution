package com.pblgllgs.sb2batchflowexecution.controller.request;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferPaymentDTO {

    private Double availableBalance;
    private Double amountPaid;
    private Boolean isEnabled;
}
