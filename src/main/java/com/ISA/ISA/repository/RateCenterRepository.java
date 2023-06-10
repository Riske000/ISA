package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.RateCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateCenterRepository extends JpaRepository<RateCenter, Integer> {
    RateCenter findByUserAndMedicalCenter(User user, MedicalCenter medicalCenter);

    boolean existsByUserAndMedicalCenter(User user, MedicalCenter medicalCenter);

    List<RateCenter> findByMedicalCenter(MedicalCenter medicalCenter);
}
