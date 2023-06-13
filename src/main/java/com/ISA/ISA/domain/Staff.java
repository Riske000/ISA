package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Staff extends Entity{
    private String name;
    private String surname;
}
