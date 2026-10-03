package com.setu.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "occasion_bookings")
public class OccasionBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "donor_id")
    private User donor;

    @ManyToOne @JoinColumn(name = "ngo_id")
    private NGO ngo;

    private String occasionType;   // Birthday, Anniversary, Festival, ...
    private String occasionTitle;  // sirf "Other" me bharna hai

    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Integer guestCount;
    private String contactPhone;

    @Column(length = 1000)
    private String description;

    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, CANCELLED

    @Column(length = 500)
    private String ngoResponse;

    private LocalDateTime createdAt = LocalDateTime.now();

    public OccasionBooking() {}

    // JSP helpers
    public String getDisplayTitle() {
        if ("Other".equals(occasionType) && occasionTitle != null && !occasionTitle.isBlank()) {
            return occasionTitle;
        }
        return occasionType;
    }

    public String getIcon() {
        if (occasionType == null) return "🎉";
        return switch (occasionType) {
            case "Birthday" -> "🎂";
            case "Anniversary" -> "💍";
            case "Festival" -> "🪔";
            case "Memorial" -> "🕯️";
            case "Graduation" -> "🎓";
            default -> "🎉";
        };
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }
    public NGO getNgo() { return ngo; }
    public void setNgo(NGO ngo) { this.ngo = ngo; }
    public String getOccasionType() { return occasionType; }
    public void setOccasionType(String occasionType) { this.occasionType = occasionType; }
    public String getOccasionTitle() { return occasionTitle; }
    public void setOccasionTitle(String occasionTitle) { this.occasionTitle = occasionTitle; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Integer getGuestCount() { return guestCount; }
    public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNgoResponse() { return ngoResponse; }
    public void setNgoResponse(String ngoResponse) { this.ngoResponse = ngoResponse; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}