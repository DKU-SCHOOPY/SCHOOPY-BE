package com.schoopy.back.global.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {
    private final JavaMailSender javaMailSender;

    private final String SUBJECT = "[SCHOOPY] 인증메일입니다.";

    public boolean sendCertificationMail(String email, String certificationNumber) {

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            
            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setFrom("nomagold57@gmail.com");
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;

    }

    private String getCertificationMessage (String certificationNumber) {
        
        String certificationMessage = "";
        
        certificationMessage = certificationMessage + "<h1 style='text-align: center;'>[SCHOOPY] 인증메일</h1>";
        certificationMessage = certificationMessage + "<h3 style='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>";
        certificationMessage = certificationMessage + certificationNumber + "</strong></h3>";
        return certificationMessage;
    }
}
