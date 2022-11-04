package com.ISA.ISA.Domain.DTO;

import com.ISA.ISA.Domain.MedicalCenter;
import com.ISA.ISA.Domain.Term;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MedicalCenterDTO {

    private String centerName;
    private String adress;
    private String description;
    private double averageRating;
    private int termID;

    public static MedicalCenterDTO convertToDTO(MedicalCenter medicalCenter){
        MedicalCenterDTO dto = new MedicalCenterDTO();

        dto.setCenterName(medicalCenter.getCenterName());
        dto.setAdress(medicalCenter.getAdress());
        dto.setDescription(medicalCenter.getDescription());
        dto.setAverageRating(medicalCenter.getAverageRating());
        dto.setTermID(medicalCenter.getId());

        return dto;
    }

    public static MedicalCenter convertBack(MedicalCenterDTO dto){
        MedicalCenter medicalCenter = new MedicalCenter();

        medicalCenter.setCenterName(dto.getCenterName());
        medicalCenter.setAdress(dto.getAdress());
        medicalCenter.setDescription(dto.getDescription());
        medicalCenter.setAverageRating(dto.getAverageRating());
        medicalCenter.setTerm(null);

        return medicalCenter;
    }
}
