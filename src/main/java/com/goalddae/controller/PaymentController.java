package com.goalddae.controller;

import com.goalddae.config.PaymentProperties;
import com.goalddae.dto.payment.CancelPaymentDTO;
import com.goalddae.dto.payment.GetPaymentPropertiesDTO;
import com.goalddae.dto.payment.RequestPaymentInfoDTO;
import com.goalddae.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentProperties paymentProperties;

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentProperties paymentProperties, PaymentService paymentService){
        this.paymentProperties = paymentProperties;
        this.paymentService = paymentService;
    }


    @RequestMapping("/getPaymentProperties")
    public ResponseEntity<GetPaymentPropertiesDTO> getPaymentProperties(){
        GetPaymentPropertiesDTO paymentPropertiesDTO = GetPaymentPropertiesDTO.builder()
                .franchiseKey(paymentProperties.getFranchiseKey())
                .build();
        return ResponseEntity.ok(paymentPropertiesDTO);
    }

    @RequestMapping(value = "/verifyIamPort", method = RequestMethod.POST)
    public IamportResponse<Payment> verifyIamPort(@RequestBody RequestPaymentInfoDTO paymentInfoDTO) throws IamportResponseException, IOException {
        IamportResponse<Payment> irsp = paymentService.paymentLookup(paymentInfoDTO.getImpUid());
        paymentService.verifyIamPortService(irsp, paymentInfoDTO.getAmount(), paymentInfoDTO.getFieldId());

        return irsp;
    }

    @PostMapping("/cancelPayment")
    public IamportResponse<Payment> cancelPayment(@RequestBody CancelPaymentDTO cancelPaymentDTO) throws Exception {
        IamportResponse<Payment> lookup = paymentService.paymentLookup(cancelPaymentDTO.getImpUid());
        return paymentService.cancelPayment(cancelPaymentDTO, lookup);
    }
}
