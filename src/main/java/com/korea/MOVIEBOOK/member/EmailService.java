package com.korea.MOVIEBOOK.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String link) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        // MimeMessage 객체 생성
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        // MimeMessage를 도와주는 헬퍼 객체 생성
        helper.setTo(to); // 이메일 받는 사람
        helper.setSubject("회원가입 인증메일 입니다."); // 이메일 제목
        helper.setText("<p>회원가입 인증절차를 완료하려면 다음 링크를 클릭하세요 : <a href='" + link + "'>회원가입 인증</a></p>",
                true); // 이메일 내용을 html 형식으로 설정
        javaMailSender.send(message); // 이메일 전송
    }
}
