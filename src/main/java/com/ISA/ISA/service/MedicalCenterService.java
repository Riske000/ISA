package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;


import java.util.List;

public interface MedicalCenterService {
    MedicalCenter add(MedicalCenterDTO medicalCenterDTO);
    MedicalCenter edit(MedicalCenterDTO medicalCenterDTO);
    void delete(int id);
    MedicalCenter findById(int id);
    List<MedicalCenter> getAll();
}
