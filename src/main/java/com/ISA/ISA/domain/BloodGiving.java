package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class BloodGiving extends Entity{
    @ManyToOne
    private User user;
    private Date date;
    private int duration;
    private double price;
    private String medicalCenterName;

}
