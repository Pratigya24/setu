package com.setu.config;

import com.setu.entity.User;
import com.setu.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AdminSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@setu.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setName("SETU Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode("Admin@123"));
            admin.setPhone("9999999999"); // used for the OTP step at login
            admin.setRole("ADMIN");
            admin.setAddress("Head Office");
            userRepository.save(admin);

            System.out.println("=========================================");
            System.out.println("Default ADMIN account created:");
            System.out.println("   Email:    " + adminEmail);
            System.out.println("   Password: Admin@123");
            System.out.println("Change this password after first login.");
            System.out.println("=========================================");
        }
    }
}