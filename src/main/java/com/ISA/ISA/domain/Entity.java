package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass

public class Entity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int id;
    private boolean deleted = false;


}

