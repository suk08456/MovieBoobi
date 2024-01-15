package com.korea.MOVIEBOOK.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PaymentDTO {

    private Long m_id;
    private String m_email;
    private String s_name;
    private String s_phone;
    private String o_shipno;
    private String o_paidAmount;
    private String o_paytype;
}
