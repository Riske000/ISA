package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Supplies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliesRepository extends JpaRepository<Supplies, Integer> {
    Supplies findByMedicalCenter(MedicalCenter medicalCenter);
}
