package com.ISA.ISA.service;

import com.ISA.ISA.domain.DTO.MedicalCenterDTO;
import com.ISA.ISA.domain.MedicalCenter;
import com.ISA.ISA.domain.Term;
import com.ISA.ISA.domain.User;
import com.ISA.ISA.domain.enums.SortMode;
import com.ISA.ISA.repository.MedicalCenterRepository;
import com.ISA.ISA.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
@Service
public class MedicalCenterServiceImpl implements MedicalCenterService{
    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private TermRepository termRepository;

    @Override
    public MedicalCenter add(MedicalCenterDTO medicalCenterDTO){
        MedicalCenter medicalCenter = new MedicalCenter();

        medicalCenter = MedicalCenterDTO.convertBack(medicalCenterDTO);
        medicalCenter = medicalCenterRepository.save(medicalCenter);
        return medicalCenter;
    }

    @Override
    public MedicalCenter edit(MedicalCenterDTO medicalCenterDTO){
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(medicalCenterDTO.getId());
        if (medicalCenter.isEmpty()){
            return null;
        }

        medicalCenter.get().setCenterName(medicalCenterDTO.getCenterName());
        medicalCenter.get().setAdress(medicalCenterDTO.getAddress());
        medicalCenter.get().setDescription(medicalCenterDTO.getDescription());
        medicalCenter.get().setAverageRating(medicalCenterDTO.getAverageRating());

        return medicalCenterRepository.save(medicalCenter.get());
    }

    @Override
    public void delete(int id){
        Optional<MedicalCenter> medicalCenter = medicalCenterRepository.findById(id);
        if (medicalCenter.isPresent()){
        medicalCenter.get().setDeleted(true);
        }
    }

    @Override
    public Optional<MedicalCenter> findById(int id){
        return medicalCenterRepository.findById(id);
    }

    @Override
    public List<MedicalCenter> getAll(){
        return medicalCenterRepository.findAll();
    }

    @Override
    public Page<MedicalCenter> getSorted(String field, int pageNo, int pageSize, String sortMode){
        Pageable paging;
        String s = "";

        if(sortMode.equals("Ascending")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field));
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, field));
        }

        return medicalCenterRepository.findAll(paging);
    }

    public int getNumberOfCenters(){
        return getAll().size();
    }

}
