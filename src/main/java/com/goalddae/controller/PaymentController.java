package com.goalddae.controller;

import com.goalddae.config.PaymentProperties;
import com.goalddae.dto.payment.GetPaymentPropertiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentProperties paymentProperties;

    @Autowired
    public PaymentController(PaymentProperties paymentProperties){
        this.paymentProperties = paymentProperties;
    }

    @RequestMapping("/getPaymentProperties")
    public ResponseEntity<GetPaymentPropertiesDTO> getPaymentProperties(){
        GetPaymentPropertiesDTO paymentPropertiesDTO = GetPaymentPropertiesDTO.builder()
                .franchiseKey(paymentProperties.getFranchiseKey())
                .pg(paymentProperties.getPg())
                .build();
        return ResponseEntity.ok(paymentPropertiesDTO);
    }
}
