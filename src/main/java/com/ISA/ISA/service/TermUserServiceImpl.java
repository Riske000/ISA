package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermUserDTO;
import com.ISA.ISA.domain.TermUser;
import com.ISA.ISA.repository.TermRepository;
import com.ISA.ISA.repository.TermUserRepository;
import com.ISA.ISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TermUserServiceImpl implements TermUserService{
    @Autowired
    private TermUserRepository termUserRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TermUser add(TermUserDTO termUserDTO){
        TermUser termUser = TermUserDTO.convertBack(termUserDTO);
        termUserRepository.save(termUser);

        return termUser;
    }

    @Override
    public TermUser edit(TermUserDTO termUserDTO){
        TermUser termUser = new TermUser();
        //??????????
        return termUser;
    }

    @Override
    public void delete(int id){
        termUserRepository.deleteById(id);
    }

    @Override
    public TermUser findById(int id){
        return termUserRepository.getReferenceById(id);
    }

    @Override
    public List<TermUser> getAll(){
        return termUserRepository.findAll();
    }
}
