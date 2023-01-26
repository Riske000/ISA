package com.ISA.ISA.domain;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
}