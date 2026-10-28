package com.setu.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "stories")
public class Story {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1500) private String content;
    @Column(length = 1000) private String imageUrl;
    private String authorName;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Story() {}
    public Story(String title, String content, String imageUrl, String authorName) {
        this.title = title; this.content = content; this.imageUrl = imageUrl; this.authorName = authorName;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getContent() { return content; }
    public void setContent(String c) { this.content = c; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String i) { this.imageUrl = i; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String a) { this.authorName = a; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}