package com.setu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.setu.entity.*;
import com.setu.repository.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatController {

    @Autowired private UserRepository userRepository;
    @Autowired private DonationRepository donationRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private ChatMessageRepository chatMessageRepository;

    @GetMapping("/chat/{donationId}")
    public String chatPage(@PathVariable Long donationId, HttpSession session, Model model) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Assignment assignment = assignmentRepository.findByDonationId(donationId).orElse(null);

        if (!canChat(user, donation, assignment)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot access this chat.");
        }

        List<ChatMessage> messages = chatMessageRepository.findByDonationIdOrderBySentAtAsc(donationId);

        model.addAttribute("donation", donation);
        model.addAttribute("assignment", assignment);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("chatRole", user.getRole());
        return "chat";
    }

    @PostMapping("/chat/{donationId}/send")
    public String send(@PathVariable Long donationId,
                       @RequestParam String content,
                       HttpSession session) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Assignment assignment = assignmentRepository.findByDonationId(donationId).orElse(null);

        if (!canChat(user, donation, assignment)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot access this chat.");
        }

        String text = content == null ? "" : content.trim();
        if (text.length() > 1000) text = text.substring(0, 1000);

        if (!text.isEmpty()) {
            ChatMessage msg = new ChatMessage();
            msg.setDonation(donation);
            msg.setSender(user);
            msg.setSenderRole(user.getRole());
            msg.setContent(text);
            chatMessageRepository.save(msg);
        }
        return "redirect:/chat/" + donationId;
    }

    // ---------- helpers ----------
    private User currentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userId == null ? null : userRepository.findById(userId).orElse(null);
    }

    /** Chat sirf accepted/completed donation par, aur sirf uske donor / NGO / assigned volunteer ke liye. */
    private boolean canChat(User user, Donation d, Assignment a) {
        boolean open = d.getNgo() != null
                && ("ACCEPTED".equals(d.getStatus()) || "COMPLETED".equals(d.getStatus()));
        if (!open) return false;

        return switch (user.getRole() == null ? "" : user.getRole()) {
            case "DONOR" -> d.getDonor() != null && d.getDonor().getId().equals(user.getId());
            case "NGO" -> user.getEmail().equals(d.getNgo().getEmail());
            case "VOLUNTEER" -> a != null && a.getVolunteer() != null
                    && user.getEmail().equals(a.getVolunteer().getEmail());
            default -> false;
        };
    }
}