package com.korea.MOVIEBOOK.payment;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public void savePayment(Long id, String payment, String paidAmount, String paymentNo, String payType, String phone, String content, String contents, String contentsID){
        Member member = this.memberRepository.findById(id).get();
        Payment payment1 = new Payment();
        payment1.setPaymentCompany(payment);
        payment1.setPaidAmount(paidAmount);
        payment1.setPaymentNo(paymentNo);
        payment1.setPayType(payType);
        payment1.setPhone(phone);
        payment1.setMember(member);
        payment1.setDateTime(LocalDateTime.now());
        payment1.setContent(content);
        payment1.setContents(contents);
        payment1.setContentsID(contentsID);
        payment1.setDateTime(LocalDateTime.now());
        this.paymentRepository.save(payment1);
    }

    public List<Payment> findPaymentListByMember(Member member){
        return this.paymentRepository.findBymember(member);
    }

    public Page<Payment> getPaymentsByMember(Member member, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("dateTime"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        return this.paymentRepository.findByMember(member, pageable);
    }
}
