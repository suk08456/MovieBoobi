package com.korea.MOVIEBOOK.payment;

import com.korea.MOVIEBOOK.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findBymember(Member member);
    Payment findByMemberAndContentsAndContentsID(Member member, String contents, String contentsID);
    Page<Payment> findByMember(Member member, Pageable pageable);
    Page<Payment> findByMemberAndContents(Member member, String contents, Pageable pageable);
}
