package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.TermUserDTO;
import com.ISA.ISA.domain.TermUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TermUserService {
    TermUser add(TermUserDTO termUserDTO);
    TermUser edit(TermUserDTO termUserDTO);
    void delete(int id);
    Optional<TermUser> findById(int id);
    List<TermUser> getAll();
}
