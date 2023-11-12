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

    public void sendEmailForPasswordReset(
            String username,
            String userEmail,
            String code
    ){

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        try{
            mimeMessageHelper.setFrom("info@home.now");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Password reset code");
            mimeMessageHelper.setText(generateEmailTextForPasswordReset(username, code), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }


    }

    private String generateEmailTextForPasswordReset(String username, String code) {

        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("username", username);
        context.setVariable("code", code);

        return template.process("email/code-new-pass", context);

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

    public void sendRegistrationEmail(String userId, String userEmail, String userName, String activationCode) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);


        try{
            mimeMessageHelper.setFrom("office@home.now");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome to myHome");
            mimeMessageHelper.setText(generateEmailTextForRegistrationLink(userId, userName, activationCode), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }

    private String generateEmailTextForRegistrationLink(String userId, String username, String activationCode) {
        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("username", username);
        context.setVariable("activation_code", activationCode);
        context.setVariable("user_id", userId);

        return template.process("email/email-register", context);
    }
}
