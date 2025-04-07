    package com.example.senagist.models;

    import jakarta.persistence.*;

    import java.time.LocalDateTime;

    @Entity
    @Table(name = "assets")
    @Inheritance(strategy = InheritanceType.JOINED)
    public class Asset {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String inventory_number;
        private String location;
        private String brand;
        private String model;
        private LocalDateTime created_at;


        public Asset(){}

        public Asset(String inventory_number, String location, String brand, String model, LocalDateTime created_at) {
            this.inventory_number = inventory_number;
            this.location = location;
            this.brand = brand;
            this.model = model;
            this.created_at = created_at;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getInventory_number() {
            return inventory_number;
        }

        public void setInventory_number(String inventory_number) {
            this.inventory_number = inventory_number;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }


        public LocalDateTime getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
        }

    }
