package com.setu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.setu.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByDonationIdOrderBySentAtAsc(Long donationId);
}