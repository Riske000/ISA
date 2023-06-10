package com.ISA.ISA.repository;

import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.StatusOfTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Integer> {
   List<Term> findAllByMedicalCenterAndStatusOfTerm(MedicalCenter medicalCenter, StatusOfTerm isFree);

   List<Term> findAllByUser(User user);

   List<Term> findAllByUserAndMedicalCenter(User user, MedicalCenter medicalCenter);
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   Optional<Term> findById(int termId);

    List<Term> findByStatusOfTerm(StatusOfTerm statusOfTerm);


    List<Term> findByDateOfTerm(Date dateTime);

    Term findByMedicalCenterIdAndDateOfTerm(Integer medicalCenterId, Date date);

    List<Term> findByMedicalCenterId(Integer medicalCenterId);

    List<Term> findByUserAndMedicalCenter(User user, MedicalCenter medicalCenter);

    List<Term> findByMedicalCenterIdAndStatusOfTerm(Integer medicalCenterId, StatusOfTerm statusOfTerm);
}
