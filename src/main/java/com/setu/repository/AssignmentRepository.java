package com.setu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setu.entity.Assignment;
import com.setu.entity.Volunteer;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByVolunteer(Volunteer volunteer);
    Optional<Assignment> findByDonationId(Long donationId);
}