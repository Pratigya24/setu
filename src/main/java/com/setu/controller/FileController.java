package com.setu.controller;

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

    @GetMapping("/admin/view-document")
    public ResponseEntity<Resource> viewDocument(@RequestParam String path) throws IOException {

        Path allowedDir = Paths.get("uploads/ngo-documents/").normalize().toAbsolutePath();
        Path resolved = Paths.get(path).normalize().toAbsolutePath();

        // Prevent path traversal outside the allowed uploads folder
        if (!resolved.startsWith(allowedDir)) {
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