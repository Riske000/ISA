package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@javax.persistence.Entity
public class RateCenter extends Entity{
    @ManyToOne
    public User user;
    @ManyToOne
    public MedicalCenter medicalCenter;
    public int rating;
}
