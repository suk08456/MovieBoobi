package com.korea.MOVIEBOOK.Payment;

import com.korea.MOVIEBOOK.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentCompany;           // 결제사

    private String paidAmount;        // 결제 금액

    private String paymentNo;         // 결제 구분자

    private String payType;           // 결제 타입

    private String phone;             // 회원 전화번호 (payment때 필수값으로 들어가야하기때문에 data에도 넣음)

    private LocalDateTime dateTime;            // 결제 일자

//    private String email;           // 회원 email

//    private String name;            // 회원 이름(= nickname)
    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    private Member member;

}
