package com.goalddae.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentDTO {
    private String impUid;
    private int checkSum;
    private String reason;
    private String refundHolder;
}
