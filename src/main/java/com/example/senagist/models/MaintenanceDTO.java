package com.example.senagist.models;

import java.time.LocalDateTime;
import java.util.Map;

public class MaintenanceDTO {
    private Long id;
    private Asset asset; // Para propiedades básicas del asset
    private String assetType; // "refrigeration", "lighting", o "general"
    private Map<String, Object> assetDetails; // Para propiedades específicas según el tipo
    private Long id_user;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String type;
    private String description;
    private String spare_parts;
    private String remarks;
    private byte[] image_1;
    private byte[] image_2;

    public MaintenanceDTO(){}

    public MaintenanceDTO(Asset asset, String assetType, Map<String, Object> assetDetails, Long id_user,
                          LocalDateTime start_date, LocalDateTime end_date, String type, String description,
                          String spare_parts, String remarks, byte[] image_1, byte[] image_2) {
        this.asset = asset;
        this.assetType = assetType;
        this.assetDetails = assetDetails;
        this.id_user = id_user;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.description = description;
        this.spare_parts = spare_parts;
        this.remarks = remarks;
        this.image_1 = image_1;
        this.image_2 = image_2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Map<String, Object> getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(Map<String, Object> assetDetails) {
        this.assetDetails = assetDetails;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpare_parts() {
        return spare_parts;
    }

    public void setSpare_parts(String spare_parts) {
        this.spare_parts = spare_parts;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getImage_1() {
        return image_1;
    }

    public void setImage_1(byte[] image_1) {
        this.image_1 = image_1;
    }

    public byte[] getImage_2() {
        return image_2;
    }

    public void setImage_2(byte[] image_2) {
        this.image_2 = image_2;
    }
}