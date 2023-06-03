package ftn.uns.ac.rs.NVTKTS20222023.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {

        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm);

        try {
            mmh.setTo(to);
            mmh.setSubject(subject);
            mmh.setText(body,true);
        } catch (MessagingException e) {
            System.out.println("EXCEPTION MAIL");
            e.printStackTrace();
        }

        javaMailSender.send(mm);
    }

}
