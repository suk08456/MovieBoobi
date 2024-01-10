package com.korea.MOVIEBOOK.payment;

import com.korea.MOVIEBOOK.member.Member;
import com.korea.MOVIEBOOK.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.awt.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberService memberService;

    @GetMapping("/payment")
    public String kakao(Model model, Principal principal, @RequestParam(value="page", defaultValue="0") int page) {
        String providerID = principal.getName();
        Member member = this.memberService.findByproviderId(providerID);
        List<Payment> payments  = this.paymentService.findPaymentListByMember(member);
        long sum = 0;

        for(int i  = 0 ; i < payments.size(); i++){
            if(payments.get(i).getContent().contains("충전")){
                sum += Long.valueOf(payments.get(i).getPaidAmount());
            } else {
                sum -= Long.valueOf(payments.get(i).getPaidAmount());
            }
        }

        Page<Payment> paging = this.paymentService.getPaymentsByMember(member,page);


        model.addAttribute("member",member);
        model.addAttribute("paging",paging);
        model.addAttribute("sum",sum);
        return "Payment/payment";
    }

    @PostMapping("/kakaoPayCheck")
    public String kakaoPayCheck(@RequestBody PaymentDTO paymentDTO) {
        String content = paymentDTO.getO_paidAmount() + "Coin 충전";
        this.paymentService.savePayment(paymentDTO.getM_id(), "kakao", paymentDTO.getO_paidAmount(), paymentDTO.getO_shipno(), paymentDTO.getO_paytype(), paymentDTO.getS_phone(), content);
        return "redriect:/kakaoPay";
    }

    @PostMapping("/tossPayCheck")
    public String tossPayCheck(@RequestBody PaymentDTO paymentDTO) {
        String content = paymentDTO.getO_paidAmount() + "Coin 충전";
        this.paymentService.savePayment(paymentDTO.getM_id(), "toss", paymentDTO.getO_paidAmount(), paymentDTO.getO_shipno(), paymentDTO.getO_paytype(), paymentDTO.getS_phone(), content);
        return "redriect:/kakaoPay";
    }
//    @PostMapping("/kakaoPayCheck")
//    public String saveKakaoPaymentData(@RequestParam("m_id") Long id,
//                                       @RequestParam("m_email") String mEmail,
//                                       @RequestParam("s_name") String sName,
//                                       @RequestParam("s_phone") String sPhone,
//                                       @RequestParam("o_shipno") String oPaidNo,
//                                       @RequestParam("o_paidAmount") String oPaidAmount,
//                                       @RequestParam("o_paytype") String oPayType) {
//        System.out.println("Received Data:");
//        System.out.println("m_email: " + mEmail);
//        System.out.println("s_name: " + sName);
//        System.out.println("s_phone: " + sPhone);
//        System.out.println("o_paidAmount: " + oPaidAmount);
//        System.out.println("o_paytype: " + oPayType);
//
//        this.paymentService.SavePayment(id, "kakao", oPaidAmount, oPaidNo, oPayType, Long.valueOf(sPhone));
//       return "redirect:/kakaoPay";
//    }
}
