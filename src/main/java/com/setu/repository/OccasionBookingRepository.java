package com.setu.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.setu.entity.NGO;
import com.setu.entity.OccasionBooking;
import com.setu.entity.User;

public interface OccasionBookingRepository extends JpaRepository<OccasionBooking, Long> {

    List<OccasionBooking> findByDonorOrderByCreatedAtDesc(User donor);

    List<OccasionBooking> findByNgoOrderByCreatedAtDesc(NGO ngo);

    // true agar same date pe koi APPROVED booking in timings se overlap kar rahi ho
    @Query("SELECT COUNT(b) > 0 FROM OccasionBooking b " +
           "WHERE b.ngo = :ngo AND b.eventDate = :date AND b.status = 'APPROVED' " +
           "AND b.startTime < :end AND b.endTime > :start")
    boolean existsApprovedOverlap(@Param("ngo") NGO ngo,
                                  @Param("date") LocalDate date,
                                  @Param("start") LocalTime start,
                                  @Param("end") LocalTime end);
}