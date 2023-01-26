package com.ISA.ISA.service;
import com.ISA.ISA.domain.EmailDetails;
import com.ISA.ISA.domain.Term;

import java.util.Date;

public interface EmailService {
    String sendConfirmationMail(EmailDetails details);
    boolean confirmMail(String email, String confirmToken);
    void sendQRCode(String email, Term term) throws Exception;
}
