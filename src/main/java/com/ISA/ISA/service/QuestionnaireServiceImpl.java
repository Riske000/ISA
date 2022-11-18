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
public class QuestionnaireServiceImpl implements QuestionnaireService{

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Questionnaire add(QuestionnaireDTO questionnaireDTO){
        Questionnaire questionnaire =  QuestionnaireDTO.convertBack(questionnaireDTO);
        questionnaire.setUser(userRepository.findById(questionnaireDTO.getUserId()).get());
        questionnaire.setDate(new Date());
        questionnaire = questionnaireRepository.save(questionnaire);
        return questionnaire;
    }

    @Override
    public Questionnaire edit(QuestionnaireDTO questionnaireDTO){
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(questionnaireDTO.getId());

        if (questionnaire.isEmpty()){
            return null;
        }

        Optional<User> user = userRepository.findById(questionnaireDTO.getUserId());
        if(user.isPresent()){
            questionnaire.get().setUser(user.get());
        }
        questionnaire.get().setDate(questionnaireDTO.date);
        questionnaire.get().setQuestion1(questionnaireDTO.isQuestion1());
        questionnaire.get().setQuestion2(questionnaireDTO.isQuestion2());
        questionnaire.get().setQuestion3(questionnaireDTO.isQuestion3());
        questionnaire.get().setQuestion4(questionnaireDTO.isQuestion4());
        questionnaire.get().setQuestion5(questionnaireDTO.isQuestion5());
        questionnaire.get().setQuestion6(questionnaireDTO.isQuestion6());
        questionnaire.get().setQuestion7(questionnaireDTO.isQuestion7());
        questionnaire.get().setQuestion8(questionnaireDTO.isQuestion8());
        questionnaire.get().setQuestion9(questionnaireDTO.isQuestion9());
        questionnaire.get().setQuestion10(questionnaireDTO.isQuestion10());
        questionnaire.get().setQuestion11(questionnaireDTO.isQuestion11());
        questionnaire.get().setQuestion12(questionnaireDTO.isQuestion12());
        questionnaire.get().setQuestion13(questionnaireDTO.isQuestion13());
        questionnaire.get().setQuestion14(questionnaireDTO.isQuestion14());
        questionnaire.get().setQuestion15(questionnaireDTO.isQuestion15());

        return questionnaireRepository.save(questionnaire.get());
    }

    @Override
    public void delete(int id) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        if (questionnaire.isPresent()) {
            questionnaire.get().setDeleted(true);
        }
    }

    @Override
    public Optional<Questionnaire> findById(int id){
        return questionnaireRepository.findById(id);
    }

    @Override
    public List<Questionnaire> getAll(){
        return questionnaireRepository.findAll();
    }
}
