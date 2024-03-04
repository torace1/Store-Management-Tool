package com.smt.project.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class EmailSender {

    @Value("${email.sender}")
    private String SENDER_EMAIL;

    @Value("${email.password}")
    private String SENDER_PASSWORD;

    public void sendEmail(String recipientEmail, String subject, String body) {
        Session session = createSession();

        try {
            Message message = createMessage(session, recipientEmail, subject, body);
            Transport.send(message);
            log.info("E-mail sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    private Message createMessage(Session session, String recipientEmail, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setContent(body, "text/html");
        return message;
    }
}
