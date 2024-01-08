package com.korea.MOVIEBOOK.Payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payment;         // 결제사

    private String paidAmount;      // 결제 금액

    private String paymentNo;       // 결제 구분자

    private String payType;         // 결제 타입

    private String name;            // 회원 이름(= nickname)

    private Long phone;             // 회원 전화번호 (payment때 필수값으로 들어가야하기때문에 data에도 넣음)

    private String email;           // 회원 email

}
