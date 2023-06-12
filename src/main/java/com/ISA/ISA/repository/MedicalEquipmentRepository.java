package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalEquipmentRepository extends JpaRepository<MedicalEquipment, Integer> {
    List<MedicalEquipment> findByDateBetween(LocalDate startOfMonth, LocalDate endOfMonth);

}
