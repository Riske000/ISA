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

    private Date dateOfTerm;
    private int duration;
    private StatusOfTerm statusOfTerm;


    public static TermDTO convertToDto(Term term){
        TermDTO termDTO = new TermDTO();

        termDTO.setDateOfTerm(term.getDateOfTerm());
        termDTO.setDuration(term.getDuration());
        term.setStatusOfTerm(term.getStatusOfTerm());

        return termDTO;
    }

    public static Term convertBack(TermDTO dto){
        Term term = new Term();

        term.setDateOfTerm(dto.getDateOfTerm());
        term.setDuration(dto.getDuration());
        term.setStatusOfTerm(dto.getStatusOfTerm());

        return term;
    }
}
