package com.setu.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final long OTP_VALID_MINUTES = 5;
    private final SecureRandom random = new SecureRandom();

    // key = user email, value = the OTP entry (code + expiry)
    private final ConcurrentHashMap<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    /** Generates a new 6-digit OTP for this email and stores it (valid for 5 minutes). */
    public String generateOtp(String email) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        Instant expiry = Instant.now().plusSeconds(OTP_VALID_MINUTES * 60);
        otpStore.put(email, new OtpEntry(code, expiry));
        return code;
    }

    /** Verifies the OTP; if correct, it is consumed (one-time use). */
    public boolean verifyOtp(String email, String code) {
        OtpEntry entry = otpStore.get(email);
        if (entry == null) {
            return false;
        }
        if (Instant.now().isAfter(entry.expiry())) {
            otpStore.remove(email);
            return false;
        }
        boolean matches = entry.code().equals(code);
        if (matches) {
            otpStore.remove(email);
        }
        return matches;
    }

    public void clearOtp(String email) {
        otpStore.remove(email);
    }

    private record OtpEntry(String code, Instant expiry) {}
}