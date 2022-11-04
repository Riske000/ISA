package com.ISA.ISA.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class TermUser extends Entity{

    private User user;
    private Term term;
}
