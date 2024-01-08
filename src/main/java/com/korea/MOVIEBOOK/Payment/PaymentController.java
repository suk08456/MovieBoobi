package com.korea.MOVIEBOOK.Payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/kakaoPay")
    public String kakao() {
        return "Payment/payment";
    }

    @PostMapping("placeorder.do")
    public String saveKakaoPaymentData(@RequestParam("m_email") String mEmail,
                                       @RequestParam("s_name") String sName,
                                       @RequestParam("s_phone") String sPhone,
                                       @RequestParam("o_shipno") String oPaidNo,
                                       @RequestParam("o_paidAmount") String oPaidAmount,
                                       @RequestParam("o_paytype") String oPayType) {
        System.out.println("Received Data:");
        System.out.println("m_email: " + mEmail);
        System.out.println("s_name: " + sName);
        System.out.println("s_phone: " + sPhone);
        System.out.println("o_paidAmount: " + oPaidAmount);
        System.out.println("o_paytype: " + oPayType);

        this.paymentService.SavePayment("kakao", oPaidAmount, oPaidNo, oPayType, sName, Long.valueOf(sPhone), mEmail);
       return "redirect:/kakaoPay";
    }
}
