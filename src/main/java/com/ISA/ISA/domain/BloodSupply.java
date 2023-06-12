package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class BloodSupply extends Entity{

    private double given;
    private double taken;
    private LocalDate date;

    public BloodSupply(double given, double taken, LocalDate date) {
        this.given = given;
        this.taken = taken;
        this.date = date;
    }
}
