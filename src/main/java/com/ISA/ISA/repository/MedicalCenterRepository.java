package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<MedicalCenter> findAll(Pageable pageable);

    List<MedicalCenter> findByCenterNameContainingIgnoreCase(String centerName);

    List<MedicalCenter> findByCenterNameContainingIgnoreCaseAndAdressContainingIgnoreCase(String centerName, String address);

    List<MedicalCenter> findByAdressContainingIgnoreCase(String adress);

    List<MedicalCenter> findByAverageRatingGreaterThanEqual(Double minRating);

}
