package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.BloodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Review extends Entity{
    @OneToOne
    private Term term;
    private Integer needleNo;
    private Integer cottonWoolNo;
    private Integer alcoholNo;
    private double deciliters;
    private BloodType bloodType;
}
