package com.ISA.ISA.repository;

import com.ISA.ISA.domain.Questionnaire;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
        Questionnaire findOneByUser(User user);
        Optional<Questionnaire> findOneByIdAndDeleted(int id, boolean deleted);

        Questionnaire findOneByUserAndDeleted(User user, boolean deleted);

}
