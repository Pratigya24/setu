package com.setu.services;

import com.setu.entity.NGO;
import com.setu.entity.Volunteer;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final NGOService ngoService;
    private final VolunteerService volunteerService;

    public AdminService(
            NGOService ngoService,
            VolunteerService volunteerService) {

        this.ngoService = ngoService;
        this.volunteerService = volunteerService;
    }

    public NGO approveNGO(Long id) {
        return ngoService.approveNGO(id);
    }

    public Volunteer approveVolunteer(Long id) {
        return volunteerService.approveVolunteer(id);
    }
}