package bg.softuni.myhome.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Locale;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine template;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine template) {
        this.javaMailSender = javaMailSender;
        this.template = template;
    }

    public void sendEmailWithOffers(
            String userEmail,
            String userName,
            String searchId
    ){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);


        try{
            mimeMessageHelper.setFrom("office@home.now");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Offers for Home");
            mimeMessageHelper.setText(generateEmailTextForOffers(userName, searchId), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }

    public void sendEmailToSecurityDept(
            String user,
            String pageUri
    ){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);


        try{
            mimeMessageHelper.setFrom("info@home.now");
            mimeMessageHelper.setTo("security@home.now");
            mimeMessageHelper.setSubject("Auth violation attempt");
            mimeMessageHelper.setText(generateEmailTextForSecurityIssue(user, pageUri), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }



    private String generateEmailTextForOffers(String username, String searchId){
        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("username", username);
        context.setVariable("searchId", searchId);

        return template.process("email/email-offers", context);
    }


    private String generateEmailTextForSecurityIssue(String user, String pageUri){
        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("user", user);
        context.setVariable("pageUri", pageUri);

        return template.process("email/email-auth", context);
    }
}
