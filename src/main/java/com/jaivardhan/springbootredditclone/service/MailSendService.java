package com.jaivardhan.springbootredditclone.service;

import com.jaivardhan.springbootredditclone.exceptions.SpringRedditException;
import com.jaivardhan.springbootredditclone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailSendService {

    private  MailContentBuilder mailContentBuilder;
    private  JavaMailSender javaMailSender;
    private  String recipientMail;

    @Autowired
    public MailSendService(MailContentBuilder mailContentBuilder, JavaMailSender javaMailSender,@Value("${recipientemail}")String recipientMail) {
        this.mailContentBuilder = mailContentBuilder;
        this.javaMailSender = javaMailSender;
        this.recipientMail = recipientMail;
    }

    @Async
    public void sendMail(NotificationEmail notificationEmail)
    {

        System.out.println(recipientMail);
        MimeMessagePreparator messagePreparator=(mimeMessage)->{
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(recipientMail);
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));

        };
        try{
            javaMailSender.send(messagePreparator);
            log.info("Activitaion message sent");
        }catch (MailPreparationException e)
        {
            throw new SpringRedditException("Exception occured while preparing the mail to "+notificationEmail.getRecipient());

        }catch (MailParseException e)
        {
            throw new SpringRedditException("Exception occured while parsing the mail to "+notificationEmail.getRecipient());

        }catch (MailAuthenticationException e)
        {
            throw new SpringRedditException("Exception occured while authenticating to mail server the mail to "+notificationEmail.getRecipient());

        }catch (MailSendException e)
        {
            throw new SpringRedditException("Exception occured while sending the mail to "+notificationEmail.getRecipient());

        }
        catch (MailException e)
        {
            throw new SpringRedditException("Exception occured while sending the mail to "+notificationEmail.getRecipient());

        }
    }

}
