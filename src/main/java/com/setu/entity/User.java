package com.setu.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    private String role;

    // Example:
    // DONOR
    // ADMIN
    // NGO
    // VOLUNTEER

    private String address;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Constructors

    public User() {
    }

    public User(String name, String email, String password,
                String phone, String role, String address) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    
    

 // ... existing fields ke saath ...
 private String resetToken;
 private LocalDateTime resetTokenExpiry;

 // ... existing getters-setters ke saath ...
 public String getResetToken() {
     return resetToken;
 }

 public void setResetToken(String resetToken) {
     this.resetToken = resetToken;
 }

 public LocalDateTime getResetTokenExpiry() {
     return resetTokenExpiry;
 }

 public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
     this.resetTokenExpiry = resetTokenExpiry;
 }
}