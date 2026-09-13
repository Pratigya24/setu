package com.setu.services;
import com.setu.entity.Donation;
import com.setu.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    public List<Donation> getDonationsByNgo(Long ngoId) {
        return donationRepository.findByNgoId(ngoId);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}