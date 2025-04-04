package com.example.senagist.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_asset", nullable = false)
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String type;
    private String description;
    private String spare_parts;
    private String remarks;

    @Lob
    @Column(name = "image_1", columnDefinition = "LONGBLOB")
    private byte[] image_1;
    @Lob
    @Column(name = "image_2", columnDefinition = "LONGBLOB")
    private byte[] image_2;

    private String status;
    private LocalDateTime created_at;

    public Maintenance() {
    }

    public Maintenance(Asset asset, User user, LocalDateTime start_date, LocalDateTime end_date,
                       String type, String description, String spare_parts,
                       String remarks, byte[] image_1, byte[] image_2, String status) {
        this.asset = asset;
        this.user = user;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.description = description;
        this.spare_parts = spare_parts;
        this.remarks = remarks;
        this.image_1 = image_1;
        this.image_2 = image_2;
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
