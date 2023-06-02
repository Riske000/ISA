package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.enums.SortMode;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MedicalCenterService {
    MedicalCenter add(MedicalCenterDTO medicalCenterDTO);
    MedicalCenter edit(MedicalCenterDTO medicalCenterDTO);
    void delete(int id);
    Optional<MedicalCenter> findById(int id);
    List<MedicalCenter> getAll();
    Page<MedicalCenter> getSorted(String field, int pageNo, int pageSize, String sortMode);
    int getNumberOfCenters();

    List<Term> getFreeTermsByMedicalCenterId(Integer medicalCenterId);

    MedicalCenterDTO updateWorkingHours(int id, LocalTime startTime, LocalTime endTime);
}
