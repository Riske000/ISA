package com.ISA.ISA.controller;

import com.ISA.ISA.domain.EmailDetails;
import com.ISA.ISA.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emailConfirm")
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendConfirmationMail(details);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping ("/confirm/{email}/{confirmToken}")
    public ResponseEntity<?> confirmMail(@PathVariable String email, @PathVariable String confirmToken){
        boolean confirmed = emailService.confirmMail(email, confirmToken);

        if(confirmed){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }
}
