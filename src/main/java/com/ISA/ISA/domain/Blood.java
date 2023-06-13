package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.BloodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Blood extends Entity{
    private Double deciliters;
    private BloodType bloodType;
    @ManyToOne
    private MedicalCenter medicalCenter;
}
