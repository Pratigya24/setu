package com.setu.controller;

import com.setu.entity.*;
import com.setu.repository.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;
    @Autowired private VolunteerRepository volunteerRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ---------- REGISTER ----------
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String role,
                            @RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam(required = false) String address,
                            @RequestParam String password,
                            Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("errorMsg", "An account already exists with this email.");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        userRepository.save(user);

        switch (role) {
            case "NGO" -> {
                NGO ngo = new NGO();
                ngo.setName(name);
                ngo.setEmail(email);
                ngo.setPhone(phone);
                ngo.setAddress(address);
                ngo.setApproved(false);
                ngoRepository.save(ngo);
            }
            case "VOLUNTEER" -> {
                Volunteer volunteer = new Volunteer();
                volunteer.setName(name);
                volunteer.setEmail(email);
                volunteer.setPhone(phone);
                volunteer.setAddress(address);
                volunteer.setActive(true);
                volunteer.setApproved(false);
                volunteerRepository.save(volunteer);
            }
        }

        return "redirect:/login";
    }

    // ---------- LOGIN ----------
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         Model model) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty() || !encoder.matches(password, userOpt.get().getPassword())) {
            model.addAttribute("errorMsg", "Invalid email or password.");
            return "login";
        }

        User user = userOpt.get();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("role", user.getRole());

        return switch (user.getRole()) {
            case "DONOR" -> "redirect:/donor/dashboard";
            case "NGO" -> "redirect:/ngo/dashboard";
            case "VOLUNTEER" -> "redirect:/volunteer/dashboard";
            case "ADMIN" -> "redirect:/admin/dashboard";
            default -> "redirect:/";
        };
    }

    // ---------- LOGOUT ----------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------- FORGOT PASSWORD ----------
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam String email, Model model) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("errorMsg", "No account found with this email.");
            return "forgot-password";
        }

        model.addAttribute("email", email);
        return "reset-password";
    }

    // ---------- RESET PASSWORD ----------
    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam String email,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match.");
            model.addAttribute("email", email);
            return "reset-password";
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("errorMsg", "Account not found.");
            return "forgot-password";
        }

        User user = userOpt.get();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        model.addAttribute("successMsg", "Password reset successful! Please login.");
        return "login";
    }
}