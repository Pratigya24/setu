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
    @Query("UPDATE Donation d SET d.ngo = :ngo, d.status = 'ACCEPTED' WHERE d.id = :id AND d.ngo IS NULL")
    int claimDonation(@Param("id") Long id, @Param("ngo") NGO ngo);
}