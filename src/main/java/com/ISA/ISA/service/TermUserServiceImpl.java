package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermUserDTO;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.TermUser;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.TermUserRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TermUserServiceImpl implements TermUserService{
    @Autowired
    private TermUserRepository termUserRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TermUser add(TermUserDTO termUserDTO){
        TermUser termUser = new TermUser();
        termUser.setUser(userRepository.findById(termUserDTO.getUserID()).get());
        termUser.setTerm(termRepository.findById(termUserDTO.getTermID()).get());

        termUser = termUserRepository.save(termUser);

        return termUser;
    }

    @Override
    public TermUser edit(TermUserDTO termUserDTO){
        Optional<User> user = userRepository.findById(termUserDTO.getUserID());
        Optional<Term> term = termRepository.findById(termUserDTO.getTermID());

        if (user.isEmpty() || term.isEmpty()){
            return null;
        }

        TermUser termUser = termUserRepository.findByTermAndAndUser(term.get(), user.get());

        if(user.isPresent()){
            termUser.setUser(user.get());
        }
        if (term.isPresent()){
            termUser.setTerm(term.get());
        }

        return termUserRepository.save(termUser);
    }

    @Override
    public void delete(int id){
        Optional<TermUser> termUser = termUserRepository.findById(id);
        if (termUser.isPresent()) {
            termUser.get().setDeleted(true);
        }
    }

    @Override
    public Optional<TermUser> findById(int id){
        return termUserRepository.findById(id);
    }

    @Override
    public List<TermUser> getAll(){
        return termUserRepository.findAll();
    }
}
