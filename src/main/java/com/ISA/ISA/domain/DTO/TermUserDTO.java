package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.TermUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TermUserDTO {

    private int id;
    private int userID;
    private int termID;

    public static TermUserDTO convertToDto(TermUser termUser){
        TermUserDTO dto = new TermUserDTO();

        dto.setId(termUser.getId());
        dto.setUserID(termUser.getUser().getId());
        dto.setTermID(termUser.getTerm().getId());

        return dto;
    }

    public static TermUser convertBack(TermUserDTO dto){
        TermUser termUser = new TermUser();

        termUser.setId(dto.getId());
        termUser.setUser(null);
        termUser.setTerm(null);

        return termUser;
    }
}
