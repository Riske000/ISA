package com.ISA.ISA.service;
import com.ISA.ISA.domain.EmailDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ISA.ISA.domain.Term;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;
    @Value("${spring.mail.username}")
    private String sender;


    public String sendConfirmationMail(EmailDetails details)
    {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject("Confirmation mail");

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public boolean confirmMail(String email, String confirmToken){
        return userService.confirmMail(email, confirmToken);
    }

    public void sendQRCode(String email, Term term) throws Exception{
        String newLine = System.getProperty("line.separator");
        String data = "Your reserved term" + newLine + "Date and time of term: " + term.getDateOfTerm().toString() + newLine +
                "Medical center: " + term.getMedicalCenter().getCenterName() + newLine +
                "Duration: " + term.getDuration() + " minutes";


        ByteArrayOutputStream byteArrayOutputStream = QRCode.from(data).to(ImageType.PNG).stream();
        byte[] qrCode = byteArrayOutputStream.toByteArray();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("QR Code");
        helper.setText("QR Code for the reserved term");
        helper.addAttachment("qrcode.png", new ByteArrayResource(qrCode));
        javaMailSender.send(message);

    }


}
