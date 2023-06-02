package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.MedicalCenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

public class MedicalCenterDTO {

    private int id;
    private String centerName;
    private String address;
    private String description;
    private double averageRating;
    private LocalTime startTime;
    private LocalTime endTime;


    public static MedicalCenterDTO convertToDTO(MedicalCenter medicalCenter){
        MedicalCenterDTO dto = new MedicalCenterDTO();

        dto.setId(medicalCenter.getId());
        dto.setCenterName(medicalCenter.getCenterName());
        dto.setAddress(medicalCenter.getAdress());
        dto.setDescription(medicalCenter.getDescription());
        dto.setAverageRating(medicalCenter.getAverageRating());


        return dto;
    }

    public static MedicalCenter convertBack(MedicalCenterDTO dto){
        MedicalCenter medicalCenter = new MedicalCenter();

        medicalCenter.setId(dto.getId());
        medicalCenter.setCenterName(dto.getCenterName());
        medicalCenter.setAdress(dto.getAddress());
        medicalCenter.setDescription(dto.getDescription());
        medicalCenter.setAverageRating(dto.getAverageRating());

        return medicalCenter;
    }

}
