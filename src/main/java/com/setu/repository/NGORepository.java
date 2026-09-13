package com.setu.repository;

import com.setu.entity.NGO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NGORepository extends JpaRepository<NGO, Long> {
    Optional<NGO> findByEmail(String email);
    List<NGO> findByApprovedFalse();
    List<NGO> findByApproved(boolean approved);
}