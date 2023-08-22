package com.goalddae.service;

import com.goalddae.dto.payment.CancelPaymentDTO;
import com.goalddae.exception.VerifyIamPortException;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;

public interface PaymentService {
    public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException;
    public void verifyIamPortService(IamportResponse<Payment> irsp, int amount, long actionBoardNo) throws VerifyIamPortException;
    public IamportResponse<Payment> cancelPayment(CancelPaymentDTO cancelPaymentDTO, IamportResponse<Payment> lookup) throws Exception;
}
