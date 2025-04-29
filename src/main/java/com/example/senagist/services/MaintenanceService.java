package com.example.senagist.services;

import com.example.senagist.models.*;
import com.example.senagist.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
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

    public List<Maintenance> getByType(String type) {
        return maintenanceRepository.findByType(type);
    }
    public List<Maintenance> getByUsername(String name) {
        return maintenanceRepository.findByUser_Name(name);
    }

    @Transactional
    public Maintenance create(MaintenanceDTO maintenanceNew) {
        Maintenance maintenance = new Maintenance();
        System.out.println(maintenanceNew);
        User user = userRepository.findById(maintenanceNew.getId_user())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Asset asset = assetRepository.findByInventoryNumber(maintenanceNew.getAsset().getInventoryNumber())
                .orElse(null);
        if (asset == null) {
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
        try {
            if (maintenanceNew.getImage_1() != null && maintenanceNew.getImage_1().length > 0) {
                byte[] compressedImage1 = compressImage(maintenanceNew.getImage_1(), 0.5f);
                maintenance.setImage_1(compressedImage1);
            } else {
                System.out.println("Advertencia: image_1 es null o está vacía");
            }

            if (maintenanceNew.getImage_2() != null && maintenanceNew.getImage_2().length > 0) {
                byte[] compressedImage2 = compressImage(maintenanceNew.getImage_2(), 0.5f);
                maintenance.setImage_2(compressedImage2);
            } else {
                System.out.println("Advertencia: image_2 es null o está vacía");
            }
        } catch (IOException e) {
            System.err.println("Error al comprimir imágenes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar las imágenes", e);
        }

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
    public byte[] compressImage(byte[] imageBytes, float quality) throws IOException {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new IllegalArgumentException("La imagen proporcionada es nula o está vacía");
        }

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage originalImage = ImageIO.read(bais);
            if (originalImage == null) {
                throw new IOException("No se pudo leer la imagen. Formato no soportado o datos corruptos.");
            }

            int maxDimension = 1600;
            BufferedImage resizedImage = originalImage;

            if (originalImage.getWidth() > maxDimension || originalImage.getHeight() > maxDimension) {
                int newWidth, newHeight;

                if (originalImage.getWidth() > originalImage.getHeight()) {
                    newWidth = maxDimension;
                    newHeight = (int) (originalImage.getHeight() * ((double) maxDimension / originalImage.getWidth()));
                } else {
                    newHeight = maxDimension;
                    newWidth = (int) (originalImage.getWidth() * ((double) maxDimension / originalImage.getHeight()));
                }

                resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = resizedImage.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
                g.dispose();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

            ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(quality);

            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            jpgWriter.setOutput(ios);

            jpgWriter.write(null, new IIOImage(resizedImage, null, null), jpgWriteParam);

            ios.close();
            jpgWriter.dispose();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error al comprimir la imagen: " + e.getMessage(), e);
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
