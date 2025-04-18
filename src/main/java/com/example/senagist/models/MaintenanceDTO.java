package com.example.senagist.models;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class MaintenanceDTO {
    private Long id;
    private Asset asset;
    private String assetType;
    private Map<String, Object> assetDetails;
    private Long id_user;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String type;
    private String description;
    private String spare_parts;
    private String remarks;
    private MultipartFile image_1_file;
    private MultipartFile image_2_file;
    private byte[] image_1;
    private byte[] image_2;
    private String image1Base64;
    private String image2Base64;

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

    public MultipartFile getImage_1_file() {
        return image_1_file;
    }

    public void setImage_1_file(MultipartFile image_1_file) {
        this.image_1_file = image_1_file;
    }

    public MultipartFile getImage_2_file() {
        return image_2_file;
    }

    public void setImage_2_file(MultipartFile image_2_file) {
        this.image_2_file = image_2_file;
    }

    public String getImage1Base64() {
        return image1Base64;
    }

    public void setImage1Base64(String image1Base64) {
        this.image1Base64 = image1Base64;
    }

    public String getImage2Base64() {
        return image2Base64;
    }

    public void setImage2Base64(String image2Base64) {
        this.image2Base64 = image2Base64;
    }
    public void convertBlobsToBase64() {
        if (this.image_1 != null) {
            this.image1Base64 = Base64.getEncoder().encodeToString(this.image_1);
        }
        if (this.image_2 != null) {
            this.image2Base64 = Base64.getEncoder().encodeToString(this.image_2);
        }
    }
    public static MaintenanceDTO fromEntity(Maintenance maintenance) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(maintenance.getId());
        dto.setAsset(maintenance.getAsset());
        dto.setId_user(maintenance.getUser().getId());
        dto.setStart_date(maintenance.getStart_date());
        dto.setEnd_date(maintenance.getEnd_date());
        dto.setType(maintenance.getType());
        dto.setDescription(maintenance.getDescription());
        dto.setSpare_parts(maintenance.getSpare_parts());
        dto.setRemarks(maintenance.getRemarks());
        dto.setImage_1(maintenance.getImage_1());
        dto.setImage_2(maintenance.getImage_2());

        dto.convertBlobsToBase64();

        return dto;
    }
    @Override
    public String toString() {
        return "MaintenanceDTO{" +
                "id=" + id +
                ", asset=" + asset +
                ", assetType='" + assetType + '\'' +
                ", assetDetails=" + assetDetails +
                ", id_user=" + id_user +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", spare_parts='" + spare_parts + '\'' +
                ", remarks='" + remarks + '\'' +
                ", image_1_file=" + image_1_file +
                ", image_2_file=" + image_2_file +
                ", image_1=" + Arrays.toString(image_1) +
                ", image_2=" + Arrays.toString(image_2) +
                '}';
    }

    public static void convertBlobsToBase64(Maintenance maintenance) {
    }
}