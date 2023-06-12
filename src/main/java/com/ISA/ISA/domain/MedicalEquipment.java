package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class MedicalEquipment extends Entity{

    private int band_aids;
    private int dressings;
    private int needles;
    private LocalDate date;

    public MedicalEquipment(int band_aids, int dressings, int needles, LocalDate date) {
        this.band_aids = band_aids;
        this.dressings = dressings;
        this.needles = needles;
        this.date = date;
    }
}
