package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TermServiceImpl implements TermService{
    @Autowired
    private TermRepository termRepository;

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Term add(TermDTO termDTO){
        Term term = TermDTO.convertBack(termDTO);

        term = termRepository.save(term);
        return term;
    }

    @Override
    public Term edit(TermDTO termDTO){
        Optional<Term> term = termRepository.findById(termDTO.getId());
        if (term.isEmpty()){
            return null;
        }

        term.get().setDateOfTerm(termDTO.getDateOfTerm());
        term.get().setDuration(termDTO.getDuration());
        term.get().setStatusOfTerm(term.get().getStatusOfTerm());
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(termDTO.getMedicalCenterId());
        if (medicalCenter.isPresent()){
            term.get().setMedicalCenter(medicalCenter.get());
        }
        Optional<User> user = userRepository.findById(termDTO.getUserId());
        if (user.isPresent()){
            term.get().setUser(user.get());
        }
        return termRepository.save(term.get());
    }

    @Override
    public void delete(int id){
        Optional<Term> term = termRepository.findById(id);
        if (term.isPresent()) {
            term.get().setDeleted(true);
        }
    }

    @Override
    public Optional<Term> findById(int id){
        return termRepository.findById(id);
    }

    @Override
    public List<Term> getAll(){
        return termRepository.findAll();
    }
}
