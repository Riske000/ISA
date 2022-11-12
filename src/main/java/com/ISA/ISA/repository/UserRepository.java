package com.ISA.ISA.repository;

import com.ISA.ISA.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findOneByEmail(String email);
}
