package com.example.senagist.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lighting_equipment")
@PrimaryKeyJoinColumn(name = "id")
public class LightingEquipment extends Asset{
    @ManyToOne
    @JoinColumn(name = "id_center", nullable = false)
    private Center center;

    private String technology;
    private Double powerKW;

    public LightingEquipment(){
        super();
    }

    public LightingEquipment(String inventory_number, String location, String brand, String model,
                              LocalDateTime created_at, Center center, String technology,
                             Double powerKW) {
        super(inventory_number, location, brand, model, created_at);
        this.center = center;
        this.technology = technology;
        this.powerKW = powerKW;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
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
}
