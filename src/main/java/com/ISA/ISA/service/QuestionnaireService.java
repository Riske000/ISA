package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.DTO.QuestionnaireDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Questionnaire;

import java.util.List;
import java.util.Optional;

public interface QuestionnaireService {

    Questionnaire add(QuestionnaireDTO questionnaireDTO);
    Questionnaire edit(QuestionnaireDTO questionnaireDTO);
    void delete(int id);
    Optional<Questionnaire> findById(int id);
    List<Questionnaire> getAll();
}
