package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.StatusOfTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private String description;
    @OneToOne
    private MedicalCenter medicalCenter;
    @OneToOne
    private User user;
    @OneToOne
    private Questionnaire questionnaire;


}
