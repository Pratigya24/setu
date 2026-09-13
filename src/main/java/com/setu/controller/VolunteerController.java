package com.setu.controller;

import com.setu.entity.Volunteer;
import com.setu.services.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@CrossOrigin
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Volunteer> registerVolunteer(
            @RequestBody Volunteer volunteer) {

        return ResponseEntity.ok(
                volunteerService.registerVolunteer(volunteer)
        );
    }

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {

        return ResponseEntity.ok(
                volunteerService.getAllVolunteers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteer(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                volunteerService.getVolunteerById(id)
        );
    }

//    @GetMapping("/ngo/{ngoId}")
//    public ResponseEntity<List<Volunteer>> getByNGO(
//            @PathVariable Long ngoId) {
//
//        return ResponseEntity.ok(
//                volunteerService.getVolunteersByNgo(ngoId)
//        );
//    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Volunteer> approveVolunteer(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                volunteerService.approveVolunteer(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVolunteer(
            @PathVariable Long id) {

        volunteerService.deleteVolunteer(id);

        return ResponseEntity.ok("Volunteer deleted successfully");
    }
}