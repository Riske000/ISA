package com.ISA.ISA.repository;

import com.ISA.ISA.domain.TermUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermUserRepository extends JpaRepository<TermUser, Integer> {
}
