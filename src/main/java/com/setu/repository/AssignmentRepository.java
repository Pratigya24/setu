package com.setu.repository;

import com.setu.entity.Assignment;
import com.setu.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByVolunteer(Volunteer volunteer);
}