package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MedicalCenter extends Entity{

    private String centerName;
    private String adress;
    private String description;
    private double averageRating;
    private Term term;

}
