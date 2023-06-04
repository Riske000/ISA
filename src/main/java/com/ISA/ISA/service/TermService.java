package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TermService {
    Term add(TermDTO termDTO);
    Term edit(TermDTO termDTO);
    void delete(int id);
    Optional<Term> findById(int id);
    List<Term> getAll();
    List<Term >getAllForSelectedCenter(int medicalCenterId);
    Term reserveTerm(int termId, int userId);
    Term cancelTerm(int termId, int userId);
    List<Term> getReservedTermForUser(int userId);

    List<Term> getAllFreeTerms();


    List<MedicalCenter> searchMedicalCentersByDateTime(LocalDateTime dateTime);
}
