package bg.softuni.myhome.service;

import bg.softuni.myhome.model.view.OfferView;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.List;
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
            List<OfferView> offers
    ){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);


        try{
            mimeMessageHelper.setFrom("office@home.now");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Offers for Home");
            mimeMessageHelper.setText(generateEmailText(userName, offers), true);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }




    private String generateEmailText(String username, List<OfferView> offers){
        Context context = new Context();
        context.setLocale(Locale.getDefault());
        context.setVariable("username", username);
        context.setVariable("offers", offers);

        return template.process("email-offers", context);
    }
}
