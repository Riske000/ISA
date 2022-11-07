package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.StatusOfTerm;
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
