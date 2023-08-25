package com.goalddae.service;

import com.goalddae.config.PaymentProperties;
import com.goalddae.dto.payment.CancelPaymentDTO;
import com.goalddae.exception.VerifyIamPortException;
import com.goalddae.repository.SoccerFieldRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService{
    private IamportClient iamportClient;
    private final PaymentProperties paymentProperties;
    private final SoccerFieldRepository soccerFieldRepository;

    @Autowired
    public PaymentServiceImpl(PaymentProperties paymentProperties, SoccerFieldRepository soccerFieldRepository){
        this.paymentProperties = paymentProperties;
        this.soccerFieldRepository = soccerFieldRepository;
    }

    @Override
    public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException {
        iamportClient = new IamportClient(paymentProperties.getRestApiKey(), paymentProperties.getRestApiSecret());
        return iamportClient.paymentByImpUid(impUid);
    }

    @Transactional
    public void verifyIamPortService(IamportResponse<Payment> irsp, int amount, long fieldId) throws VerifyIamPortException {
        //실제로 결제된 금액과 아임포트 서버쪽 결제내역 금액과 같은지 확인
        //이때 가격은 BigDecimal 데이터 타입으로 주로 금융쪽에서 정확한 값표현을 위해 사용
        //int형으로 비교해주기 위해 형변환 필요
        if(irsp.getResponse().getAmount().intValue()!=amount){
            throw new VerifyIamPortException("결제 금액 위/변조: SERVER");
        }

        //DB에서 물건가격과 실제 결제금액이 일치하는지 확인, 만약 다르면 예외 발생
        if(amount != soccerFieldRepository.findById(fieldId).get().getReservationFree()){
            throw new VerifyIamPortException("결제 금액 위/변조: DB");
        }
    }

    @Override
    public IamportResponse<Payment> cancelPayment(CancelPaymentDTO cancelPaymentDTO, IamportResponse<Payment> lookup) throws Exception {
        CancelData data = new CancelData(lookup.getResponse().getImpUid(), true);
        data.setReason(cancelPaymentDTO.getReason());
        data.setChecksum(BigDecimal.valueOf(cancelPaymentDTO.getCheckSum()));
        data.setRefund_holder(cancelPaymentDTO.getRefundHolder());
        return iamportClient.cancelPaymentByImpUid(data);
    }
}
