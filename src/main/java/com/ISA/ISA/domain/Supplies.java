package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Supplies extends Entity{
    private Integer needle;
    private Integer cottonWool;
    private Integer alcohol;
    @OneToOne
    private MedicalCenter medicalCenter;
}
