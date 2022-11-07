package com.ISA.ISA.repository;

import com.ISA.ISA.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
