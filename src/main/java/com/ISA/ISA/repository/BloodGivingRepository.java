package com.ISA.ISA.repository;

import com.ISA.ISA.domain.BloodGiving;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BloodGivingRepository extends JpaRepository<BloodGiving, Integer> {
    Page<BloodGiving> findAllByUser(User user, Pageable pageable);
    BloodGiving findFirstByUserOrderByDateDesc(User user);

}
