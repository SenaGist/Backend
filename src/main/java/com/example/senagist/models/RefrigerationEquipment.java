package com.example.senagist.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refrigeration_equipment ")
@PrimaryKeyJoinColumn(name = "id")
public class RefrigerationEquipment extends Asset {
    @ManyToOne
    @JoinColumn(name = "id_center", nullable = false)
    private Center center;

    @Column(name = "main_group")
    private String mainGroup;

    private String description;
    private String technology;
    private Double powerKW;

    @Column(name = "refrigerant_type")
    private String refrigerantType;

    @Column(name = "refrigerant_capacity_kg")
    private String refrigerantCapacityKg;

    @Column(name = "energy_classification")
    private String energyClassification;

    public RefrigerationEquipment(){
        super();
    }

    public RefrigerationEquipment(String inventory_number, String location, String brand, String model,
                                  LocalDateTime created_at, Center center, String mainGroup,
                                  String description, String technology, Double powerKW, String refrigerantType,
                                  String refrigerantCapacityKg, String energyClassification) {
        super(inventory_number, location, brand, model, created_at);
        this.center = center;
        this.mainGroup = mainGroup;
        this.description = description;
        this.technology = technology;
        this.powerKW = powerKW;
        this.refrigerantType = refrigerantType;
        this.refrigerantCapacityKg = refrigerantCapacityKg;
        this.energyClassification = energyClassification;
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

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Double getPowerKW() {
        return powerKW;
    }

    public void setPowerKW(Double powerKW) {
        this.powerKW = powerKW;
    }

    public String getRefrigerantType() {
        return refrigerantType;
    }

    public void setRefrigerantType(String refrigerantType) {
        this.refrigerantType = refrigerantType;
    }

    public String getRefrigerantCapacityKg() {
        return refrigerantCapacityKg;
    }

    public void setRefrigerantCapacityKg(String refrigerantCapacityKg) {
        this.refrigerantCapacityKg = refrigerantCapacityKg;
    }

    public String getEnergyClassification() {
        return energyClassification;
    }

    public void setEnergyClassification(String energyClassification) {
        this.energyClassification = energyClassification;
    }
}
