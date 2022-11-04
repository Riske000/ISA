package com.ISA.ISA.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor

public class User extends Entity{

    private String email;
    private String password;
    private String name;
    private String surname;
    private String adrress;
    private String city;
    private String country;
    private String mobilePhone;
    private int jmbg;
    private Gender gender;
    private String profession;
    private String jobDescription;
    private UserType userType;
    private MedicalCenter medicalCenter;







}
