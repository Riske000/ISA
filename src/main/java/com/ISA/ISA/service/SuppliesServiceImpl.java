package com.ISA.ISA.service;

import com.ISA.ISA.domain.Supplies;
import com.ISA.ISA.repository.SuppliesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuppliesServiceImpl implements SuppliesService{

    @Autowired
    private SuppliesRepository suppliesRepository;

    public Supplies createSupplies(Supplies supplies) {
        return suppliesRepository.save(supplies);
    }

    public Supplies updateSupplies(Integer suppliesId, Supplies updatedSupplies) {
        Supplies supplies = suppliesRepository.findById(suppliesId).orElse(null);
        if (supplies != null) {
            // Ažurirajte polja za snabdevanje sa podacima iz updatedSupplies
            supplies.setNeedle(updatedSupplies.getNeedle());
            supplies.setCottonWool(updatedSupplies.getCottonWool());
            supplies.setAlcohol(updatedSupplies.getAlcohol());
            supplies.setMedicalCenter(updatedSupplies.getMedicalCenter());

            return suppliesRepository.save(supplies);
        }
        return null;
    }

    public void deleteSupplies(Integer suppliesId) {
        Optional<Supplies> suppliesOptional =  suppliesRepository.findById(suppliesId);
        Supplies supplies = suppliesOptional.get();
        supplies.setDeleted(true);
        suppliesRepository.save(supplies);
    }

}
