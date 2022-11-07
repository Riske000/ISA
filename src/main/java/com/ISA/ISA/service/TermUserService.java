package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermUserDTO;
import com.ISA.ISA.domain.TermUser;

import java.util.List;

public interface TermUserService {
    TermUser add(TermUserDTO termUserDTO);
    TermUser edit(TermUserDTO termUserDTO);
    void delete(int id);
    TermUser findById(int id);
    List<TermUser> getAll();
}
