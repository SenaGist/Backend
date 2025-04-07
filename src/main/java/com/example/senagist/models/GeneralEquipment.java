package com.example.senagist.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "general_equipment")
@PrimaryKeyJoinColumn(name = "id")
public class GeneralEquipment extends Asset{

    @ManyToOne
    @JoinColumn(name = "id_center", nullable = false)
    private Center center;

    @Column(name = "main_group")
    private String mainGroup;

    private String description;

    @Column(name = "energy_classification")
    private String energyClassification;

    @Column(name = "daily_usage_hours")
    private Double dailyUsageHours;

    @Column(name = "powerKW")
    private Double powerKW;

    public GeneralEquipment() {
        super();
    }

    public GeneralEquipment(String inventoryNumber, String location, String brand, String model, String status,
                            LocalDateTime createdAt, Center center, String mainGroup, String description,
                            String energyClassification, Double dailyUsageHours, Double powerKW) {
        super(inventoryNumber, location, brand, model, createdAt);
        this.center = center;
        this.mainGroup = mainGroup;
        this.description = description;
        this.energyClassification = energyClassification;
        this.dailyUsageHours = dailyUsageHours;
        this.powerKW = powerKW;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnergyClassification() {
        return energyClassification;
    }

    public void setEnergyClassification(String energyClassification) {
        this.energyClassification = energyClassification;
    }

    public Double getDailyUsageHours() {
        return dailyUsageHours;
    }

    public void setDailyUsageHours(Double dailyUsageHours) {
        this.dailyUsageHours = dailyUsageHours;
    }

    public Double getPowerKW() {
        return powerKW;
    }

    public void setPowerKW(Double powerKW) {
        this.powerKW = powerKW;
    }
}
