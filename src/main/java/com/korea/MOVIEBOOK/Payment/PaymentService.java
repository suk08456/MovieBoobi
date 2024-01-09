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

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public void savePayment(Long id, String payment, String paidAmount, String paymentNo, String payType, String phone){
        Member member = this.memberRepository.findById(id).get();
        Payment payment1 = new Payment();
        payment1.setPaymentCompany(payment);
        payment1.setPaidAmount(paidAmount);
        payment1.setPaymentNo(paymentNo);
        payment1.setPayType(payType);
        payment1.setPhone(phone);
        payment1.setMember(member);
        payment1.setDateTime(LocalDateTime.now());
        this.paymentRepository.save(payment1);
    }

    public List<Payment> findPaymentListByMember(Member member){
        return this.paymentRepository.findBymember(member);
    }
}
