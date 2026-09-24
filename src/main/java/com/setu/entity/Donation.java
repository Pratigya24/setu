package com.setu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private Double amount;

    private Integer quantity;

    @Column(length = 1000)
    private String pickupAddress;

    private String status;

    /*
     * Example:
     * PENDING
     * ACCEPTED
     * COMPLETED
     * REJECTED
     */

    private LocalDateTime donationDate;

    // Donor

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    // NGO receiving donation

    @ManyToOne
    @JoinColumn(name = "ngo_id")
    private NGO ngo;

    // Donation category

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructors

    public Donation() {
        this.donationDate = LocalDateTime.now();
    }

    public Donation(String title,
                    String description,
                    Double amount,
                    Integer quantity,
                    String status,
                    User donor,
                    NGO ngo,
                    Category category) {

        this.title = title;
        this.description = description;
        this.amount = amount;
        this.quantity = quantity;
        this.status = status;
        this.donor = donor;
        this.ngo = ngo;
        this.category = category;
        this.donationDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}