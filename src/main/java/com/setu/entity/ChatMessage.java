package com.setu.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne @JoinColumn(name = "sender_id")
    private User sender;

    private String senderRole; // DONOR, NGO, VOLUNTEER

    @Column(length = 1000)
    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();

    public ChatMessage() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Donation getDonation() { return donation; }
    public void setDonation(Donation donation) { this.donation = donation; }
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}