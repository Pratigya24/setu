package com.setu.services;

import com.setu.entity.Volunteer;
import com.setu.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer registerVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
    }

    public Volunteer approveVolunteer(Long id) {
        Volunteer volunteer = getVolunteerById(id);
        volunteer.setApproved(true);
        return volunteerRepository.save(volunteer);
    }

   // public List<Volunteer> getVolunteersByNgo(Long ngoId) {
     //   return volunteerRepository.findByNgoId(ngoId);
    //}

    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
}