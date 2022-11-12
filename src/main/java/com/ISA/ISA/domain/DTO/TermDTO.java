package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.enums.StatusOfTerm;
import com.ISA.ISA.domain.Term;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TermDTO {

    private int id;
    private Date dateOfTerm;
    private int duration;
    private StatusOfTerm statusOfTerm;
    private int medicalCenterId;
    private int userId;

    public static TermDTO convertToDto(Term term){
        TermDTO termDTO = new TermDTO();

        termDTO.setId(termDTO.getId());
        termDTO.setDateOfTerm(term.getDateOfTerm());
        termDTO.setDuration(term.getDuration());
        termDTO.setStatusOfTerm(term.getStatusOfTerm());
        termDTO.setMedicalCenterId(term.getMedicalCenter().getId());
        termDTO.setUserId(term.getUser().getId());
        return termDTO;
    }

    public static Term convertBack(TermDTO dto){
        Term term = new Term();

        term.setId(dto.getId());
        term.setDateOfTerm(dto.getDateOfTerm());
        term.setDuration(dto.getDuration());
        term.setStatusOfTerm(dto.getStatusOfTerm());
        term.setMedicalCenter(null);
        term.setUser(null);
        return term;
    }
}
