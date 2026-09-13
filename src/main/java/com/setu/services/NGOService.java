package com.setu.services;

import com.setu.entity.NGO;
import com.setu.repository.NGORepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NGOService {

    private final NGORepository ngoRepository;

    public NGOService(NGORepository ngoRepository) {
        this.ngoRepository = ngoRepository;
    }

    public NGO registerNGO(NGO ngo) {
        return ngoRepository.save(ngo);
    }

    public List<NGO> getAllNGOs() {
        return ngoRepository.findAll();
    }

    public NGO getNGOById(Long id) {
        return ngoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NGO not found"));
    }

    public List<NGO> getApprovedNGOs() {
        return ngoRepository.findByApproved(true);
    }

    public NGO approveNGO(Long id) {
        NGO ngo = getNGOById(id);
        ngo.setApproved(true);
        return ngoRepository.save(ngo);
    }

    public void deleteNGO(Long id) {
        ngoRepository.deleteById(id);
    }
}