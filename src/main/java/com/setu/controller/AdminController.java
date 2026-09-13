package com.setu.controller;
import com.setu.entity.NGO;
import com.setu.entity.Volunteer;
import com.setu.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/ngo/{id}/approve")
    public ResponseEntity<NGO> approveNGO(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.approveNGO(id)
        );
    }

    @PutMapping("/volunteer/{id}/approve")
    public ResponseEntity<Volunteer> approveVolunteer(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminService.approveVolunteer(id)
        );
    }
}