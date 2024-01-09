package com.korea.MOVIEBOOK.Payment;

import com.korea.MOVIEBOOK.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findBymember(Member member);
}
