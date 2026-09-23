package com.setu.controller;

import com.setu.entity.NGO;
import com.setu.entity.User;
import com.setu.repository.NGORepository;
import com.setu.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;

    private static final Path ALLOWED_DIR = Paths.get("uploads/ngo-documents/").normalize().toAbsolutePath();

    // ---------- ADMIN: view any NGO's document (used from Manage Charitable Homes) ----------
    @GetMapping("/admin/view-document")
    public ResponseEntity<Resource> viewDocumentAsAdmin(@RequestParam String path) throws IOException {
        return serveFile(path);
    }

    // ---------- NGO: view only THEIR OWN uploaded document/photo ----------
    @GetMapping("/ngo/view-document")
    public ResponseEntity<Resource> viewOwnDocument(@RequestParam String path, HttpSession session) throws IOException {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        NGO ngo = ngoRepository.findByEmail(user.getEmail()).orElse(null);
        if (ngo == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Make sure the NGO can only open files that belong to their own profile,
        // not some other NGO's document by guessing/changing the path in the URL.
        boolean ownsThisFile = path.equals(ngo.getVerificationDocumentPath())
                || path.equals(ngo.getHomePhotoPath());

        if (!ownsThisFile) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return serveFile(path);
    }

    private ResponseEntity<Resource> serveFile(String path) throws IOException {
        Path resolved = Paths.get(path).normalize().toAbsolutePath();

        // Prevent path traversal outside the allowed uploads folder
        if (!resolved.startsWith(ALLOWED_DIR)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Resource resource = new UrlResource(resolved.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(resolved);

        return ResponseEntity.ok()
                .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resolved.getFileName() + "\"")
                .body(resource);
    }
}