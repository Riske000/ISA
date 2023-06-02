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
public class UserDTO {

    private int id;
    private String email;
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
    private int medicalCenterID;

    private int loyaltyPoints;
    private String loyaltyCategory;

    public static UserDTO convertToDto(User user)
    {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setCountry(user.getCountry());
        dto.setMobilePhone(user.getMobilePhone());
        dto.setJmbg(user.getJmbg());
        dto.setGender(user.getGender());
        dto.setProfession(user.getProfession());
        dto.setJobDescription(user.getJobDescription());
        dto.setUserType(user.getUserType());
        dto.setLoyaltyPoints(user.getLoyaltyPoints());
        dto.setLoyaltyCategory(user.getLoyaltyCategory());
        if (user.getMedicalCenter() != null) {
            dto.setMedicalCenterID(user.getMedicalCenter().getId());
        }

        return dto;
    }


    public static User convertBack(UserDTO dto)
    {
        User user = new User();

        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
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
        user.setUserType(dto.getUserType());
        user.setLoyaltyPoints(dto.getLoyaltyPoints());
        user.setLoyaltyCategory(dto.getLoyaltyCategory());

        return user;
    }
}
