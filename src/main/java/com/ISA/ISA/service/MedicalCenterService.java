package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
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

    List<Term> getTermsByMedicalCenterId(Integer medicalCenterId);

    MedicalCenterDTO updateWorkingHours(int id, LocalTime startTime, LocalTime endTime);


    List<MedicalCenter> searchMedicalCentersByCenterNameAndAdressIgnoreCase(String centerName, String adress);

    List<MedicalCenter> searchMedicalCentersByCenterNameIgnoreCase(String centerName);

    List<MedicalCenter> searchMedicalCentersByAdressIgnoreCase(String adress);

    List<MedicalCenter> filterMedicalCentersByRating(Double minRating);

    MedicalCenterDTO updateCenterInfo(int id, String centerName, String address, String description, Double averageRating);

    double averageRating(Integer medicalCenterId);

    List<User> usersWhoVisited(Integer medicalCenterId);
}
