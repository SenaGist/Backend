package com.example.senagist.services;

import com.example.senagist.models.*;
import com.example.senagist.repositories.*;
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
    @Autowired
    private RefrigerationEquipmentRepository refrigerationEquipmentRepository;
    @Autowired
    private LightingEquipmentRepository lightingEquipmentRepository;
    @Autowired
    private GeneralEquipmentRepository generalEquipmentRepository;
    @Autowired
    private CenterRepository centerRepository;

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
        System.out.println(maintenanceNew);
        User user = userRepository.findById(maintenanceNew.getId_user())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Long centerId;
        if (maintenanceNew.getAssetDetails() != null && maintenanceNew.getAssetDetails().get("centerId") != null) {
            if (maintenanceNew.getAssetDetails().get("centerId") instanceof Integer) {
                centerId = ((Integer) maintenanceNew.getAssetDetails().get("centerId")).longValue();
            } else if (maintenanceNew.getAssetDetails().get("centerId") instanceof Long) {
                centerId = (Long) maintenanceNew.getAssetDetails().get("centerId");
            } else if (maintenanceNew.getAssetDetails().get("centerId") instanceof String) {
                centerId = Long.parseLong((String) maintenanceNew.getAssetDetails().get("centerId"));
            } else {
                centerId = null;
            }
        } else {
            centerId = null;
        }

        Center center = null;
        if (centerId != null) {
            center = centerRepository.findById(centerId)
                    .orElseThrow(() -> new EntityNotFoundException("Center not found with ID: " + centerId));
        }

        Asset asset = assetRepository.findByInventoryNumber(maintenanceNew.getAsset().getInventoryNumber())
                .orElse(null);
        if (asset == null) {
            switch (maintenanceNew.getAssetType()) {
                case "refrigeration":
                    RefrigerationEquipment refrigeration = new RefrigerationEquipment();
                    copyBaseAssetProperties(maintenanceNew.getAsset(), refrigeration);
                    if (maintenanceNew.getAssetDetails() != null) {
                        refrigeration.setCenter(center);
                        refrigeration.setMainGroup((String) maintenanceNew.getAssetDetails().get("mainGroup"));
                        refrigeration.setDescription((String) maintenanceNew.getAssetDetails().get("description"));
                        refrigeration.setTechnology((String) maintenanceNew.getAssetDetails().get("technology"));

                        if (maintenanceNew.getAssetDetails().get("powerKW") != null) {
                            if (maintenanceNew.getAssetDetails().get("powerKW") instanceof Number) {
                                refrigeration.setPowerKW(((Number) maintenanceNew.getAssetDetails().get("powerKW")).doubleValue());
                            } else if (maintenanceNew.getAssetDetails().get("powerKW") instanceof String) {
                                try {
                                    refrigeration.setPowerKW(Double.parseDouble((String) maintenanceNew.getAssetDetails().get("powerKW")));
                                } catch (NumberFormatException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        refrigeration.setRefrigerantType((String) maintenanceNew.getAssetDetails().get("refrigerantType"));
                        refrigeration.setRefrigerantCapacityKg((String) maintenanceNew.getAssetDetails().get("refrigerantCapacityKg"));
                        refrigeration.setEnergyClassification((String) maintenanceNew.getAssetDetails().get("energyClassification"));
                    }
                    refrigeration.setCreated_at(LocalDateTime.now());
                    asset = refrigerationEquipmentRepository.save(refrigeration);
                    break;

                case "lighting":
                    LightingEquipment lighting = new LightingEquipment();
                    copyBaseAssetProperties(maintenanceNew.getAsset(), lighting);
                    if (maintenanceNew.getAssetDetails() != null) {
                        lighting.setCenter(center);
                        lighting.setTechnology((String) maintenanceNew.getAssetDetails().get("technology"));

                        if (maintenanceNew.getAssetDetails().get("powerKW") != null) {
                            if (maintenanceNew.getAssetDetails().get("powerKW") instanceof Number) {
                                lighting.setPowerKW(((Number) maintenanceNew.getAssetDetails().get("powerKW")).doubleValue());
                            } else if (maintenanceNew.getAssetDetails().get("powerKW") instanceof String) {
                                try {
                                    lighting.setPowerKW(Double.parseDouble((String) maintenanceNew.getAssetDetails().get("powerKW")));
                                } catch (NumberFormatException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    lighting.setCreated_at(LocalDateTime.now());
                    asset = lightingEquipmentRepository.save(lighting);
                    break;

                case "general":
                    GeneralEquipment general = new GeneralEquipment();
                    copyBaseAssetProperties(maintenanceNew.getAsset(), general);
                    if (maintenanceNew.getAssetDetails() != null) {
                        general.setCenter(center);
                        general.setMainGroup((String) maintenanceNew.getAssetDetails().get("mainGroup"));
                        general.setDescription((String) maintenanceNew.getAssetDetails().get("description"));
                        general.setEnergyClassification((String) maintenanceNew.getAssetDetails().get("energyClassification"));

                        if (maintenanceNew.getAssetDetails().get("dailyUsageHours") != null) {
                            if (maintenanceNew.getAssetDetails().get("dailyUsageHours") instanceof Number) {
                                general.setDailyUsageHours(((Number) maintenanceNew.getAssetDetails().get("dailyUsageHours")).doubleValue());
                            } else if (maintenanceNew.getAssetDetails().get("dailyUsageHours") instanceof String) {
                                try {
                                    general.setDailyUsageHours(Double.parseDouble((String) maintenanceNew.getAssetDetails().get("dailyUsageHours")));
                                } catch (NumberFormatException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        if (maintenanceNew.getAssetDetails().get("powerKW") != null) {
                            if (maintenanceNew.getAssetDetails().get("powerKW") instanceof Number) {
                                general.setPowerKW(((Number) maintenanceNew.getAssetDetails().get("powerKW")).doubleValue());
                            } else if (maintenanceNew.getAssetDetails().get("powerKW") instanceof String) {
                                try {
                                    general.setPowerKW(Double.parseDouble((String) maintenanceNew.getAssetDetails().get("powerKW")));
                                } catch (NumberFormatException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                    general.setCreated_at(LocalDateTime.now());
                    asset = generalEquipmentRepository.save(general);
                    break;

                default:
                    throw new IllegalArgumentException("Asset type not recognized");
            }
        }


        maintenance.setAsset(asset);
        maintenance.setUser(user);
        maintenance.setCreated_at(LocalDateTime.now());
        maintenance.setStart_date(maintenanceNew.getStart_date());
        maintenance.setDescription(maintenanceNew.getDescription());
        maintenance.setType(maintenanceNew.getType());
        maintenance.setSpare_parts(maintenanceNew.getSpare_parts());
        maintenance.setRemarks(maintenanceNew.getRemarks());
        maintenance.setImage_1(maintenanceNew.getImage_1());
        maintenance.setImage_2(maintenanceNew.getImage_2());

        return maintenanceRepository.save(maintenance);
    }


    private void copyBaseAssetProperties(Asset source, Asset target) {
        if (source != null) {
            target.setInventoryNumber(source.getInventoryNumber());
            target.setLocation(source.getLocation());
            target.setBrand(source.getBrand());
            target.setModel(source.getModel());
        }
    }


    public Maintenance update(MaintenanceDTO maintenanceDTO) {
        return maintenanceRepository.findById(maintenanceDTO.getId())
                .map(maintenance -> {
                    maintenance.setAsset(maintenanceDTO.getAsset());
                    maintenance.setEnd_date(maintenanceDTO.getEnd_date());
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
