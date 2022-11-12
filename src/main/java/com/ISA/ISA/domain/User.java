package com.ISA.ISA.domain;

import com.ISA.ISA.domain.enums.Gender;
import com.ISA.ISA.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@javax.persistence.Entity
public class User extends Entity{

    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String city;
    private String country;
    private String mobilePhone;
    private int jmbg;
    private Gender gender;
    private String profession;
    private String jobDescription;
    private UserType userType;
    @ManyToOne
    private MedicalCenter medicalCenter;


    public String getRole(){
        return userType.toString();
    }
}
