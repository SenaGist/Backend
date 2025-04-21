package com.example.senagist.repositories;

import com.example.senagist.models.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByUserId(Long user_id);
    List<Maintenance> findByAssetId(Long asset_id);
    List<Maintenance> findByType(String type);
    List<Maintenance> findByUser_Name(String name);
}
