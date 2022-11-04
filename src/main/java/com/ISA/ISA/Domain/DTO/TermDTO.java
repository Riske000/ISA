package com.ISA.ISA.Domain.DTO;

import com.ISA.ISA.Domain.StatusOfTerm;
import com.ISA.ISA.Domain.Term;
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
        term.setStatusOfTerm(dto.statusOfTerm);

        return term;
    }
}
