package com.korea.MOVIEBOOK.Payment;

import com.korea.MOVIEBOOK.member.MemberService;
import com.korea.MOVIEBOOK.member.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberService memberService;

    @GetMapping("/kakaoPay")
    public String kakao(Model model) {
        return "Payment/payment";
    }

    @PostMapping("placeorder.do")
    public String saveKakaoPaymentData(@RequestParam("m_id") Long id,
                                       @RequestParam("m_email") String mEmail,
                                       @RequestParam("s_name") String sName,
                                       @RequestParam("s_phone") String sPhone,
                                       @RequestParam("o_shipno") String oPaidNo,
                                       @RequestParam("o_paidAmount") String oPaidAmount,
                                       @RequestParam("m_id") String oPayType) {
        System.out.println("Received Data:");
        System.out.println("m_email: " + mEmail);
        System.out.println("s_name: " + sName);
        System.out.println("s_phone: " + sPhone);
        System.out.println("o_paidAmount: " + oPaidAmount);
        System.out.println("o_paytype: " + oPayType);

        this.paymentService.SavePayment(id, "kakao", oPaidAmount, oPaidNo, oPayType, Long.valueOf(sPhone));
       return "redirect:/kakaoPay";
    }
}
