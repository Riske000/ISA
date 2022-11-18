package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.enums.Gender;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDTO {
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
    private int medicalCenterID;

    public static RegistrationDTO convertToDto(User user)
    {
        RegistrationDTO dto = new RegistrationDTO();

        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setCity(user.getCity());
        dto.setMobilePhone(user.getMobilePhone());
        dto.setJmbg(user.getJmbg());
        dto.setGender(user.getGender());
        dto.setProfession(user.getProfession());
        dto.setJobDescription(user.getJobDescription());
        dto.setMedicalCenterID(user.getMedicalCenter().getId());

        return dto;
    }

    public static User convertBack(RegistrationDTO dto)
    {
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry());
        user.setMobilePhone(dto.getMobilePhone());
        user.setJmbg(dto.getJmbg());
        user.setGender(dto.getGender());
        user.setProfession(dto.getProfession());
        user.setJobDescription(dto.getJobDescription());
        user.setMedicalCenter(null); // menjaj kada budes imao u repo

        return user;
    }
}
