package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TermServiceImpl implements TermService{
    @Autowired
    private TermRepository termRepository;

    @Override
    public Term add(TermDTO termDTO){
        Term term = TermDTO.convertBack(termDTO);
        // moze li ovako?
        return term;
    }

    @Override
    public Term edit(TermDTO termDTO){
        Term term = new Term();
        //kad pitas
        return term;
    }

    @Override
    public void delete(int id){
        termRepository.deleteById(id);
    }

    @Override
    public Term findById(int id){
        return termRepository.getReferenceById(id);
    }

    @Override
    public List<Term> getAll(){
        return termRepository.findAll();
    }
}
