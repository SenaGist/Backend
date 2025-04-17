package com.example.senagist.controllers;

import com.example.senagist.models.Maintenance;
import com.example.senagist.models.MaintenanceDTO;
import com.example.senagist.services.MaintenanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/maintenances")
public class MaintenanceController {

    private static final Log log = LogFactory.getLog(MaintenanceController.class);
    @Autowired
    private MaintenanceService maintenanceService;

    private MaintenanceDTO convertToDTO(Maintenance m) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(m.getId());
        dto.setAsset(m.getAsset());
        dto.setId_user(m.getUser().getId());
        dto.setStart_date(m.getStart_date());
        dto.setEnd_date(m.getEnd_date());
        dto.setType(m.getType());
        dto.setDescription(m.getDescription());
        dto.setSpare_parts(m.getSpare_parts());
        dto.setRemarks(m.getRemarks());

        if (m.getImage_1() != null) {
            dto.setImage1Base64("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(m.getImage_1()));
        }
        if (m.getImage_2() != null) {
            dto.setImage2Base64("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(m.getImage_2()));
        }

        return dto;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Maintenance> maintenances = maintenanceService.getAll();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id) {
        List<Maintenance> maintenances = maintenanceService.getByUserId(id);

        List<MaintenanceDTO> dtos = maintenances.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<?> getByAssetId(@PathVariable Long id) {
        List<Maintenance> maintenances = maintenanceService.getByAsset(id);
        return ResponseEntity.ok(maintenances);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMaintenance(
            @RequestPart("maintenanceDTO") String jsonData,
            @RequestPart(value = "image_1", required = false) MultipartFile image1,
            @RequestPart(value = "image_2", required = false) MultipartFile image2
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MaintenanceDTO maintenanceDTO = objectMapper.readValue(jsonData, MaintenanceDTO.class);
            maintenanceDTO.setImage_1(image1.getBytes());
            maintenanceDTO.setImage_2(image2.getBytes());

            Maintenance maintenance = maintenanceService.create(maintenanceDTO);
            MaintenanceDTO dto = convertToDTO(maintenance);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Error al crear mantenimiento", e);
            return ResponseEntity.badRequest().body(null);
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
