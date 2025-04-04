package com.example.senagist.services;

import com.example.senagist.models.Asset;
import com.example.senagist.models.Maintenance;
import com.example.senagist.models.MaintenanceDTO;
import com.example.senagist.models.User;
import com.example.senagist.repositories.AssetRepository;
import com.example.senagist.repositories.MaintenanceRepository;
import com.example.senagist.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetRepository assetRepository;


    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    public List<Maintenance> getByUserId(Long user_id) {
        return maintenanceRepository.findByUserId(user_id);
    }

    public List<Maintenance> getByAsset(Long asset_id) {
        return maintenanceRepository.findByAssetId(asset_id);
    }

    @Transactional
    public Maintenance create(MaintenanceDTO maintenanceNew) {
        Maintenance maintenance = new Maintenance();
        User user = userRepository.findById(maintenanceNew.getId_user())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Asset asset = assetRepository.findById(maintenanceNew.getId_asset())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        maintenance.setAsset(asset);
        maintenance.setUser(user);
        maintenance.setCreated_at(LocalDateTime.now());
        maintenance.setStart_date(maintenanceNew.getStart_date());
        maintenance.setDescription(maintenanceNew.getDescription());
        maintenance.setType(maintenanceNew.getType());
        maintenance.setSpare_parts(maintenanceNew.getSpare_parts());
        maintenance.setRemarks(maintenanceNew.getRemarks());
        maintenance.setStatus(maintenanceNew.getStatus());
        maintenance.setImage_1(maintenanceNew.getImage_1());
        maintenance.setImage_2(maintenanceNew.getImage_2());

        return maintenanceRepository.save(maintenance);
    }

    public Maintenance update(MaintenanceDTO maintenanceDTO) {
        Asset asset = assetRepository.findById(maintenanceDTO.getId_asset())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));
        return maintenanceRepository.findById(maintenanceDTO.getId())
                .map(maintenance -> {
                    maintenance.setAsset(asset);
                    maintenance.setEnd_date(maintenanceDTO.getEnd_date());
                    maintenance.setStatus(maintenanceDTO.getStatus());
                    maintenance.setImage_1(maintenanceDTO.getImage_1());
                    maintenance.setImage_2(maintenanceDTO.getImage_2());
                    return maintenanceRepository.save(maintenance);
                })
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found"));
    }

    public void delete(Long id) {
        if (maintenanceRepository.existsById(id)) {
            maintenanceRepository.deleteById(id);
        }
    }
}
