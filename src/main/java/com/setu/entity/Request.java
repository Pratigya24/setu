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
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private Integer quantity;

    private String status;

    /*
     * PENDING
     * APPROVED
     * FULFILLED
     * REJECTED
     */

    private String urgency;

    /*
     * Low
     * Medium
     * High
     */

    private LocalDateTime requestDate;

    // NGO creating the request

    @ManyToOne
    @JoinColumn(name = "ngo_id")
    private NGO ngo;

    // Category of required item

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructors

    public Request() {
        this.requestDate = LocalDateTime.now();
    }

    public Request(String title,
                   String description,
                   Integer quantity,
                   String status,
                   NGO ngo,
                   Category category) {

        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.ngo = ngo;
        this.category = category;
        this.requestDate = LocalDateTime.now();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
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