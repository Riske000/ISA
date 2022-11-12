package com.ISA.ISA.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class TermUser extends Entity{

    @ManyToOne
    private User user;
    @ManyToOne
    private Term term;
}
