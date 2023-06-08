package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    User save(User user);

    List<User> findAllByMedicalCenterAndUserType(MedicalCenter medicalCenter, UserType userType);
}
