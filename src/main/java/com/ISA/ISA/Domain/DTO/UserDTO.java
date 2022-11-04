package com.ISA.ISA.Domain.DTO;

import com.ISA.ISA.Domain.Gender;
import com.ISA.ISA.Domain.User;
import com.ISA.ISA.Domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String email;
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
    private int medicalCenterID;

    public static UserDTO convertToDto(User user)
    {
        UserDTO dto = new UserDTO();

        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAdrress(user.getAdrress());
        dto.setCity(user.getCity());
        dto.setCity(user.getCity());
        dto.setMobilePhone(user.getMobilePhone());
        dto.setJmbg(user.getJmbg());
        dto.setGender(user.getGender());
        dto.setProfession(user.getProfession());
        dto.setJobDescription(user.getJobDescription());
        dto.setUserType(user.getUserType());
        dto.setMedicalCenterID(user.getMedicalCenter().getId());

        return dto;
    }

    public static User convertBack(UserDTO dto)
    {
        User user = new User();
        
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAdrress(dto.getAdrress());
        user.setCity(dto.getCity());
        user.setCountry(dto.getCountry());
        user.setMobilePhone(dto.getMobilePhone());
        user.setJmbg(dto.getJmbg());
        user.setGender(dto.getGender());
        user.setProfession(dto.getProfession());
        user.setJobDescription(dto.getJobDescription());
        user.setUserType(dto.getUserType());
        user.setMedicalCenter(null);

        return user;
    }
}
