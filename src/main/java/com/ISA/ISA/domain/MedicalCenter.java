package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class MedicalCenter extends Entity{

    private String centerName;
    private String adress;
    private String description;
    private double averageRating;
    private LocalTime startTime;
    private LocalTime endTime;

}
