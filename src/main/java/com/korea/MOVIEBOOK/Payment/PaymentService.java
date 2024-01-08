package com.korea.MOVIEBOOK.Payment;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void SavePayment(String payment, String paidAmount, String paymentNo, String payType, String name, Long phone, String email){
        Payment payment1 = new Payment();
        payment1.setPayment(payment);
        payment1.setPaidAmount(paidAmount);
        payment1.setPaymentNo(paymentNo);
        payment1.setPayType(payType);
        payment1.setName(name);
        payment1.setPhone(phone);
        payment1.setEmail(email);
        this.paymentRepository.save(payment1);
    }
}
