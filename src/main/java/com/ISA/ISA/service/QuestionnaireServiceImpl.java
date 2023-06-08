package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.QuestionnaireDTO;
import com.ISA.ISA.domain.Questionnaire;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.QuestionnaireRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Questionnaire add(QuestionnaireDTO questionnaireDTO) {
        Questionnaire questionnaire = QuestionnaireDTO.convertBack(questionnaireDTO);

        Optional<User> user = userRepository.findById(questionnaireDTO.getUserId());
        if (user.isEmpty()) {
            return null;
        }
        Questionnaire oldQuestionnaire = questionnaireRepository.findOneByUserAndDeleted(user.get(), false);
        if (oldQuestionnaire != null) {
            oldQuestionnaire.setDeleted(true);
        }

        if (questionnaire.isQuestion1()) {
            return null;
        }

        questionnaire.setUser(user.get());
        questionnaire.setDate(new Date());

        questionnaire = questionnaireRepository.save(questionnaire);
        return questionnaire;
    }

    @Override
    public Questionnaire edit(QuestionnaireDTO questionnaireDTO) {
        Optional<User> user = userRepository.findById(questionnaireDTO.getUserId());
        Questionnaire questionnaire = questionnaireRepository.findOneByUserAndDeleted(user.get(), false);


        if (user.isPresent()) {
            questionnaire.setUser(user.get());
        }
        questionnaire.setDate(new Date());
        questionnaire.setQuestion1(questionnaireDTO.isQuestion1());
        questionnaire.setQuestion2(questionnaireDTO.isQuestion2());
        questionnaire.setQuestion3(questionnaireDTO.isQuestion3());
        questionnaire.setQuestion4(questionnaireDTO.isQuestion4());
        questionnaire.setQuestion5(questionnaireDTO.isQuestion5());
        questionnaire.setQuestion6(questionnaireDTO.isQuestion6());
        questionnaire.setQuestion7(questionnaireDTO.isQuestion7());
        questionnaire.setQuestion8(questionnaireDTO.isQuestion8());
        questionnaire.setQuestion9(questionnaireDTO.isQuestion9());
        questionnaire.setQuestion10(questionnaireDTO.isQuestion10());
        questionnaire.setQuestion11(questionnaireDTO.isQuestion11());
        questionnaire.setQuestion12(questionnaireDTO.isQuestion12());
        questionnaire.setQuestion13(questionnaireDTO.isQuestion13());
        questionnaire.setQuestion14(questionnaireDTO.isQuestion14());
        questionnaire.setQuestion15(questionnaireDTO.isQuestion15());

        return questionnaireRepository.save(questionnaire);
    }

    @Override
    public void delete(int id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if (questionnaire.isPresent()) {
            questionnaire.get().setDeleted(true);
        }
    }

    @Override
    public Optional<Questionnaire> findById(int id) {
        return questionnaireRepository.findOneByIdAndDeleted(id, false);
    }

    @Override
    public List<Questionnaire> getAll() {
        return questionnaireRepository.findAll();
    }

    public Questionnaire getLastForUser(int userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return new Questionnaire();
        }

        Questionnaire questionnaire = questionnaireRepository.findOneByUserAndDeleted(user.get(), false);

        return questionnaire;
    }
}