package com.korea.MOVIEBOOK.Payment;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberRepository;
import com.korea.MOVIEBOOK.member.MemberService;
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
    private final MemberRepository memberRepository;

    public void SavePayment(Long id, String payment, String paidAmount, String paymentNo, String payType, Long phone){
        Member member = this.memberRepository.findById(id).get();

        Payment payment1 = new Payment();
        payment1.setPayment(payment);
        payment1.setPaidAmount(paidAmount);
        payment1.setPaymentNo(paymentNo);
        payment1.setPayType(payType);
        payment1.setPhone(phone);
        payment1.setMember(member);
        this.paymentRepository.save(payment1);
    }
}
