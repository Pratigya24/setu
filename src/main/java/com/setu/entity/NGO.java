package com.setu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ngos")
public class NGO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String description;

    private boolean approved = false;

    private String registrationNumber;

    private Integer capacity;

    private String verificationDocumentPath;

    private String homePhotoPath;

    // Default constructor
    public NGO() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getVerificationDocumentPath() {
        return verificationDocumentPath;
    }

    public void setVerificationDocumentPath(String verificationDocumentPath) {
        this.verificationDocumentPath = verificationDocumentPath;
    }

    public String getHomePhotoPath() {
        return homePhotoPath;
    }

    public void setHomePhotoPath(String homePhotoPath) {
        this.homePhotoPath = homePhotoPath;
    }
}