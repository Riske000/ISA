package com.ISA.ISA.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@javax.persistence.Entity
public class Questionnaire extends Entity{
    @ManyToOne
    public User user;
    public Date date;
    public boolean question1;
    public boolean question2;
    public boolean question3;
    public boolean question4;
    public boolean question5;
    public boolean question6;
    public boolean question7;
    public boolean question8;
    public boolean question9;
    public boolean question10;
    public boolean question11;
    public boolean question12;
    public boolean question13;
    public boolean question14;
    public boolean question15;

}
