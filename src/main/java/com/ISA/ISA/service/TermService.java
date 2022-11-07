package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.Term;


import java.util.List;

public interface TermService {
    Term add(TermDTO termDTO);
    Term edit(TermDTO termDTO);
    void delete(int id);
    Term findById(int id);
    List<Term> getAll();
}
