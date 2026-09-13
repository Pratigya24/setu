package com.setu.controller;
import com.setu.entity.NGO;
import com.setu.services.NGOService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ngos")
@CrossOrigin
public class NGOController {

    private final NGOService ngoService;

    public NGOController(NGOService ngoService) {
        this.ngoService = ngoService;
    }

    @PostMapping
    public ResponseEntity<NGO> registerNGO(
            @RequestBody NGO ngo) {

        return ResponseEntity.ok(
                ngoService.registerNGO(ngo)
        );
    }

    @GetMapping
    public ResponseEntity<List<NGO>> getAllNGOs() {

        return ResponseEntity.ok(
                ngoService.getAllNGOs()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NGO> getNGO(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ngoService.getNGOById(id)
        );
    }

    @GetMapping("/approved")
    public ResponseEntity<List<NGO>> getApprovedNGOs() {

        return ResponseEntity.ok(
                ngoService.getApprovedNGOs()
        );
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<NGO> approveNGO(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ngoService.approveNGO(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNGO(
            @PathVariable Long id) {

        ngoService.deleteNGO(id);

        return ResponseEntity.ok("NGO deleted successfully");
    }
}