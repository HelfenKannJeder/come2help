package de.helfenkannjeder.come2help.server.util.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailService {

    public void send(final String from, final String to, final String subject, final String text) {
        try {
            JavaMailSender javaMailSender = new JavaMailSenderImpl();
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, false);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            throw new MailServiceException(e.getMessage());
        }
    }
}
