package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class News extends Entity{
    @ManyToOne
    public MedicalCenter medicalCenter;
    public LocalDate dateOfNews;
    public String description;
}
