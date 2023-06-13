package com.ISA.ISA.repository;

import com.ISA.ISA.domain.Blood;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.enums.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Integer> {
    Blood findByBloodTypeAndMedicalCenter(BloodType bloodType, MedicalCenter medicalCenter);

    List<Blood> findByMedicalCenter(MedicalCenter medicalCenter);
}
