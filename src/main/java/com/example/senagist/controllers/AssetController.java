package com.example.senagist.controllers;

import com.example.senagist.models.GeneralEquipment;
import com.example.senagist.models.LightingEquipment;
import com.example.senagist.models.RefrigerationEquipment;
import com.example.senagist.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(assetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(assetService.getById(id));
    }

    @GetMapping("/general")
    public ResponseEntity<?> getAllGeneralEquipment(){
        return ResponseEntity.ok(assetService.getAllGeneralEquipment());
    }

    @GetMapping("/refrigeration")
    public ResponseEntity<?> getAllRefrigerationEquipment(){
        return ResponseEntity.ok(assetService.getAllRefrigerationEquipment());
    }

    @GetMapping("/lighting")
    public ResponseEntity<?> getAllLightingEquipment(){
        return ResponseEntity.ok(assetService.getAllLightingEquipment());
    }

    @PostMapping("/general")
    public ResponseEntity<?> saveGeneralEquipment (@RequestBody GeneralEquipment generalEquipment){
        assetService.saveGeneralEquipment(generalEquipment);
        return ResponseEntity.ok(Map.of("message","General equipment created"));
    }

    @PostMapping("/refrigeration")
    public ResponseEntity<?> saveRefrigerationEquipment (@RequestBody RefrigerationEquipment refrigerationEquipment){
        assetService.saveRefrigerationEquipment(refrigerationEquipment);
        return ResponseEntity.ok(Map.of("message","Refrigeration equipment created"));
    }

    @PostMapping("/lighting")
    public ResponseEntity<?> saveLightingEquipment (@RequestBody LightingEquipment lightingEquipment){
        assetService.saveLightingEquipment(lightingEquipment);
        return ResponseEntity.ok(Map.of("message","Lighting equipment created"));
    }


}
