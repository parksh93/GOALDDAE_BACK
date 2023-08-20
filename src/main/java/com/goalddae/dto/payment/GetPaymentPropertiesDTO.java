package com.goalddae.dto.payment;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentPropertiesDTO {
    private String franchiseKey;
    private String pg;
}
