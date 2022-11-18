package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalCenterRepository extends JpaRepository<MedicalCenter, Integer> {
    List<MedicalCenter> findAllByOrderByCenterNameAsc();
    List<MedicalCenter> findAllByOrderByCenterNameDesc();
    List<MedicalCenter> findAllByOrderByAdressAsc();
    List<MedicalCenter> findAllByOrderByAdressDesc();
    List<MedicalCenter> findAllByOrderByAverageRatingAsc();
    List<MedicalCenter> findAllByOrderByAverageRatingDesc();

}
