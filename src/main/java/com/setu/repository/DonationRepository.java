package com.setu.repository;

import com.setu.entity.Donation;
import com.setu.entity.User;
import com.setu.entity.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorOrderByDonationDateDesc(User donor);
    List<Donation> findByNgo(NGO ngo);
    List<Donation> findByNgoIsNullAndStatus(String status);

    List<Donation> findByNgoId(Long ngoId);

    @Modifying
    @Transactional
    @Query("UPDATE Donation d SET d.ngo = :ngo, d.status = 'ACCEPTED' " +
           "WHERE d.id = :id AND d.ngo IS NULL AND d.status = 'AVAILABLE'")
    int claimDonation(@Param("id") Long id, @Param("ngo") NGO ngo);

    // Accepted by an NGO, but no volunteer has taken the pickup yet
    @Query("SELECT d FROM Donation d WHERE d.status = 'ACCEPTED' AND d.ngo IS NOT NULL " +
           "AND NOT EXISTS (SELECT a FROM Assignment a WHERE a.donation = d) " +
           "ORDER BY d.donationDate DESC")
    List<Donation> findUnassignedAccepted();
}