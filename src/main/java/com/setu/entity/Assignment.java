package com.setu.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    private String status; // ASSIGNED, COMPLETED

    private LocalDateTime assignedDate;

    public Assignment() {
        this.assignedDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Volunteer getVolunteer() { return volunteer; }
    public void setVolunteer(Volunteer volunteer) { this.volunteer = volunteer; }
    public Donation getDonation() { return donation; }
    public void setDonation(Donation donation) { this.donation = donation; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDateTime assignedDate) { this.assignedDate = assignedDate; }

    // for JSP compatibility (assignment.requestTitle already used in existing JSPs)
    public String getRequestTitle() {
        return donation != null ? donation.getTitle() : "";
    }
}