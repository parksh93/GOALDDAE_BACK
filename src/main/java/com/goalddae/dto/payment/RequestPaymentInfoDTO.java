package com.goalddae.dto.payment;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentInfoDTO {
    private String impUid;
    private int amount;
    private long fieldId;
}
