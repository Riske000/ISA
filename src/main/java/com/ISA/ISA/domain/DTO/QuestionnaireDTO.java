package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.Questionnaire;
import com.ISA.ISA.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class QuestionnaireDTO {

    private int id;
    public int userId;
    public Date date;
    public boolean question1;
    public boolean question2;
    public boolean question3;
    public boolean question4;
    public boolean question5;
    public boolean question6;
    public boolean question7;
    public boolean question8;
    public boolean question9;
    public boolean question10;
    public boolean question11;
    public boolean question12;
    public boolean question13;
    public boolean question14;
    public boolean question15;

    public static QuestionnaireDTO convertToDto(Questionnaire questionnaire) {
        QuestionnaireDTO dto = new QuestionnaireDTO();

        dto.setId(questionnaire.getId());
        dto.setUserId(questionnaire.getUser().getId());
        dto.setDate(questionnaire.getDate());
        dto.setQuestion1(questionnaire.isQuestion1());
        dto.setQuestion2(questionnaire.isQuestion2());
        dto.setQuestion3(questionnaire.isQuestion3());
        dto.setQuestion4(questionnaire.isQuestion4());
        dto.setQuestion5(questionnaire.isQuestion5());
        dto.setQuestion6(questionnaire.isQuestion6());
        dto.setQuestion7(questionnaire.isQuestion7());
        dto.setQuestion8(questionnaire.isQuestion8());
        dto.setQuestion9(questionnaire.isQuestion9());
        dto.setQuestion10(questionnaire.isQuestion10());
        dto.setQuestion11(questionnaire.isQuestion11());
        dto.setQuestion12(questionnaire.isQuestion12());
        dto.setQuestion13(questionnaire.isQuestion13());
        dto.setQuestion14(questionnaire.isQuestion14());
        dto.setQuestion15(questionnaire.isQuestion15());

        return dto;
    }

    public static Questionnaire convertBack(QuestionnaireDTO dto) {
        Questionnaire questionnaire = new Questionnaire();

        questionnaire.setId(dto.getId());
        questionnaire.setQuestion1(dto.isQuestion1());
        questionnaire.setQuestion2(dto.isQuestion2());
        questionnaire.setQuestion3(dto.isQuestion3());
        questionnaire.setQuestion4(dto.isQuestion4());
        questionnaire.setQuestion5(dto.isQuestion5());
        questionnaire.setQuestion6(dto.isQuestion6());
        questionnaire.setQuestion7(dto.isQuestion7());
        questionnaire.setQuestion8(dto.isQuestion8());
        questionnaire.setQuestion9(dto.isQuestion9());
        questionnaire.setQuestion10(dto.isQuestion10());
        questionnaire.setQuestion11(dto.isQuestion11());
        questionnaire.setQuestion12(dto.isQuestion12());
        questionnaire.setQuestion13(dto.isQuestion13());
        questionnaire.setQuestion14(dto.isQuestion14());
        questionnaire.setQuestion15(dto.isQuestion15());

        return  questionnaire;
    }
}
