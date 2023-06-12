package com.ISA.ISA.repository;

import com.ISA.ISA.domain.BloodSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, Integer> {
    List<BloodSupply> findByDateBetween(LocalDate startOfMonth, LocalDate endOfMonth);
}
