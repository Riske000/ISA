package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.enums.SortMode;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

public interface MedicalCenterService {
    MedicalCenter add(MedicalCenterDTO medicalCenterDTO);
    MedicalCenter edit(MedicalCenterDTO medicalCenterDTO);
    void delete(int id);
    Optional<MedicalCenter> findById(int id);
    List<MedicalCenter> getAll();
    List<MedicalCenter> getSorted(String field, SortMode sortMode);
}
