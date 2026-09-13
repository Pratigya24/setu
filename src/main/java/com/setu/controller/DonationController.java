package com.setu.controller;
import com.setu.entity.Donation;
import com.setu.services.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(
            @RequestBody Donation donation) {

        return ResponseEntity.ok(
                donationService.createDonation(donation)
        );
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {

        return ResponseEntity.ok(
                donationService.getAllDonations()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                donationService.getDonationById(id)
        );
    }

    @GetMapping("/ngo/{ngoId}")
    public ResponseEntity<List<Donation>> getByNGO(
            @PathVariable Long ngoId) {

        return ResponseEntity.ok(
                donationService.getDonationsByNgo(ngoId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonation(
            @PathVariable Long id) {

        donationService.deleteDonation(id);

        return ResponseEntity.ok("Donation deleted successfully");
    }
}