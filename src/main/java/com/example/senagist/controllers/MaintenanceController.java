package com.example.senagist.controllers;

import com.example.senagist.models.Maintenance;
import com.example.senagist.models.MaintenanceDTO;
import com.example.senagist.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maintenances")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;


    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Maintenance> maintenances = maintenanceService.getAll();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id) {
        List<Maintenance> maintenances = maintenanceService.getByUserId(id);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<?> getByAssetId(@PathVariable Long id) {
        List<Maintenance> maintenances = maintenanceService.getByAsset(id);
        return ResponseEntity.ok(maintenances);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@ModelAttribute MaintenanceDTO maintenanceDTO) {
        try {
            // Convertir MultipartFile a byte[]
            if (maintenanceDTO.getImage_1_file() != null && !maintenanceDTO.getImage_1_file().isEmpty()) {
                maintenanceDTO.setImage_1(maintenanceDTO.getImage_1_file().getBytes());
            }

            if (maintenanceDTO.getImage_2_file() != null && !maintenanceDTO.getImage_2_file().isEmpty()) {
                maintenanceDTO.setImage_2(maintenanceDTO.getImage_2_file().getBytes());
            }

            maintenanceService.create(maintenanceDTO);
            return ResponseEntity.ok(Map.of("message", "Maintenance Created"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process image files: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MaintenanceDTO maintenanceDTO) {
        maintenanceService.update(maintenanceDTO);
        return ResponseEntity.ok(Map.of("message", "Maintenance updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        maintenanceService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Maintenance Deleted"));
    }
}
