package com.korea.MOVIEBOOK.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.Setter;
import lombok.extern.java.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Log
@Controller
public class SampleController {

    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;
    @GetMapping("orderComplete.do")
    public String checkKakaoPaymentData(){
        return "redirect:/kakaoPay";
    }
//    @RequestMapping("/kakaopay")
//    @ResponseBody
//    public String kakaoPayGet(Model model) {
//        try {
//            URL address = new URL("https://kapi.kakao.com/v1/payment/ready");
//            HttpURLConnection serverConnection = (HttpURLConnection) address.openConnection();  // 내가 작성한 url이랑 서버를 연결시켜주는 역할
//            serverConnection.setRequestMethod("POST");
//            serverConnection.setRequestProperty("Authorization", "KakaoAK 9c4fd30f1fac393a2d85060fb83b3599");
//            serverConnection.setRequestProperty("Content-type", "Content-type: application/x-www-form-urlencoded;charset=utf-8");
//            serverConnection.setDoOutput(true); // doOutput은 서버에 내가 건네줄게 있는지 없는지 & input은 default로 true가 들어감
//            String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=코인&quantity=1&total_amount=1000&tax_free_amount=0&approval_url=https://developers.kakao.com/success&fail_url=https://developers.kakao.com/fail&cancel_url=https://developers.kakao.com/cancel";
//            OutputStream outputStream = serverConnection.getOutputStream();   // 서버에 뭔가를 준다
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);     // 데이터 주는애 byte형식으로 보내야해(약속)
//            dataOutputStream.writeBytes(param); // 알아서 byte로 형변환
//            dataOutputStream.close();   // flush라는 함수가 있다. flush는 비운다라는 뜻(자기가 가지고 있는 데이터를 전기줄로 태워보낸다) & close 작성하면 자동 flush 됨
//
//            int result = serverConnection.getResponseCode();    // 연결결과를 알기위해서
//
//            InputStream inputStream;
//            if (result == 200) {  // http에서는 200은 정상적인 수치 그외의 수치는 에러 수치
//                inputStream = serverConnection.getInputStream();
//            } else {
//                inputStream = serverConnection.getErrorStream();
//            }
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);   // 읽는애
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);      // 형변환해주는애(원래는 bufferedReader가 형변환용은 아님)
//            return bufferedReader.readLine();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @PostMapping("/kakaoPay")
//    public String kakaoPay() {
//        log.info("kakaoPay post............................................");
//
//        return "redirect:" + kakaopay.kakaoPayReady();
//
//    }
//
//    @GetMapping("/kakaoPaySuccess")
//    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
//        log.info("kakaoPaySuccess get............................................");
//        log.info("kakaoPaySuccess pg_token : " + pg_token);
//
//    }

}
