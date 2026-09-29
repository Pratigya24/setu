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
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String postalCode;
    private String country;

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

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getFullAddress() {
        StringBuilder value = new StringBuilder();
        appendAddressPart(value, addressLine1);
        appendAddressPart(value, addressLine2);
        appendAddressPart(value, landmark);
        appendAddressPart(value, city);
        appendAddressPart(value, state);
        appendAddressPart(value, postalCode);
        appendAddressPart(value, country);
        return value.length() == 0 ? address : value.toString();
    }

    private void appendAddressPart(StringBuilder value, String part) {
        if (part != null && !part.isBlank()) {
            if (value.length() > 0) value.append(", ");
            value.append(part.trim());
        }
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