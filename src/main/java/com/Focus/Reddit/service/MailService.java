package com.Focus.Reddit.service;

import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.NotificationEmail;
import com.Focus.Reddit.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContainBuilder;

    @Async
    void sendMail(String to,NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("Aljazri@Company.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent! ");
        } catch (MailException e) {
            throw new SpringRedditException("Expectation occurred when sending the email " + notificationEmail.getRecipient());
        }

    }



}




