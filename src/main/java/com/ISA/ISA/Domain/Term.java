package com.ISA.ISA.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class Term extends Entity{

    private Date dateOfTerm;
    private int duration;
    private StatusOfTerm statusOfTerm;

}
