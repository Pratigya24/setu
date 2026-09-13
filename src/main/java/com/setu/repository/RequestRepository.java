package com.setu.repository;

import com.setu.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByNgoId(Long ngoId);
    List<Request> findByStatus(String status);
}