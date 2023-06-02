package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.StatusOfTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Term extends Entity{

    private Date dateOfTerm;
    private int duration;
    private StatusOfTerm statusOfTerm;
    @ManyToOne
    private MedicalCenter medicalCenter;
    @ManyToOne
    private User user;


}
