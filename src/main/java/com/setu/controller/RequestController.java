package com.setu.controller;

import com.setu.entity.Request;
import com.setu.services.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(
            @RequestBody Request request) {

        return ResponseEntity.ok(
                requestService.createRequest(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {

        return ResponseEntity.ok(
                requestService.getAllRequests()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                requestService.getRequestById(id)
        );
    }

    @GetMapping("/ngo/{ngoId}")
    public ResponseEntity<List<Request>> getByNGO(
            @PathVariable Long ngoId) {

        return ResponseEntity.ok(
                requestService.getRequestsByNGO(ngoId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                requestService.getRequestsByStatus(status)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(
            @PathVariable Long id) {

        requestService.deleteRequest(id);

        return ResponseEntity.ok("Request deleted successfully");
    }
}