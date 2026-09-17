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