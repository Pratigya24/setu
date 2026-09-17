package com.setu.controller;

import com.setu.entity.*;
import com.setu.repository.*;
//import com.setu.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;
    @Autowired private VolunteerRepository volunteerRepository;
//    @Autowired private EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Base folder where all NGO proof documents/photos get stored
    private static final String NGO_UPLOAD_DIR = "uploads/ngo-documents/";

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
                            @RequestParam(required = false) String registrationNumber,
                            @RequestParam(required = false) Integer capacity,
                            @RequestParam(required = false) MultipartFile verificationDocument,
                            @RequestParam(required = false) MultipartFile homePhoto,
                            Model model) {

        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("errorMsg", "An account already exists with this email.");
            return "register";
        }

        // Charitable Home (NGO) must upload a verification document
        if ("NGO".equals(role) && (verificationDocument == null || verificationDocument.isEmpty())) {
            model.addAttribute("errorMsg", "Please upload a verification document to register as a Charitable Home.");
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
                ngo.setApproved(false); // stays pending until admin approves
                ngo.setRegistrationNumber(registrationNumber);
                ngo.setCapacity(capacity);

                try {
                    Path uploadDir = Paths.get(NGO_UPLOAD_DIR);
                    Files.createDirectories(uploadDir);

                    // Save verification document (required)
                    String docFileName = System.currentTimeMillis() + "_" + verificationDocument.getOriginalFilename();
                    Files.copy(verificationDocument.getInputStream(),
                            uploadDir.resolve(docFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                    ngo.setVerificationDocumentPath(NGO_UPLOAD_DIR + docFileName);

                    // Save home/shelter photo (optional)
                    if (homePhoto != null && !homePhoto.isEmpty()) {
                        String photoFileName = System.currentTimeMillis() + "_" + homePhoto.getOriginalFilename();
                        Files.copy(homePhoto.getInputStream(),
                                uploadDir.resolve(photoFileName),
                                StandardCopyOption.REPLACE_EXISTING);
                        ngo.setHomePhotoPath(NGO_UPLOAD_DIR + photoFileName);
                    }
                } catch (IOException e) {
                    model.addAttribute("errorMsg", "Failed to upload verification document. Please try again.");
                    return "register";
                }

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

        // Block login for Charitable Homes until admin approves them
        if ("NGO".equals(user.getRole())) {
            Optional<NGO> ngoOpt = ngoRepository.findByEmail(email);
            if (ngoOpt.isPresent() && !ngoOpt.get().isApproved()) {
                model.addAttribute("errorMsg",
                        "Your Charitable Home account is pending admin verification. Please check back once it's approved.");
                return "login";
            }
        }

        // Block login for Volunteers until admin approves them
        if ("VOLUNTEER".equals(user.getRole())) {
            Optional<Volunteer> volOpt = volunteerRepository.findByEmail(email);
            if (volOpt.isPresent() && !volOpt.get().isApproved()) {
                model.addAttribute("errorMsg",
                        "Your volunteer account is pending admin approval.");
                return "login";
            }
        }

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
    public String forgotPasswordSubmit(@RequestParam String email,
                                        HttpServletRequest request,
                                        Model model) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            model.addAttribute("errorMsg", "No account found with this email.");
            return "forgot-password";
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        userRepository.save(user);

        int port = request.getServerPort();
        String portPart = (port == 80 || port == 443) ? "" : ":" + port;
        String resetLink = request.getScheme() + "://" + request.getServerName() + portPart +
                request.getContextPath() + "/reset-password?token=" + token;

//        emailService.sendPasswordResetEmail(email, resetLink);

        model.addAttribute("successMsg", "A password reset link has been sent to your email.");
        return "forgot-password";
    }

    // ---------- RESET PASSWORD ----------
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        Optional<User> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isEmpty() || userOpt.get().getResetTokenExpiry() == null
                || userOpt.get().getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("errorMsg", "This reset link is invalid or has expired.");
            return "forgot-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam String token,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       Model model) {

        Optional<User> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isEmpty() || userOpt.get().getResetTokenExpiry() == null
                || userOpt.get().getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            model.addAttribute("errorMsg", "This reset link is invalid or has expired.");
            return "forgot-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        User user = userOpt.get();
        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        model.addAttribute("successMsg", "Password reset successful! Please login.");
        return "login";
    }
}