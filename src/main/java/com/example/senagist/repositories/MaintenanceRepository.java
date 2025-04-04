package com.example.senagist.repositories;

import com.example.senagist.models.Asset;
import com.example.senagist.models.Maintenance;
import com.example.senagist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByUserId(Long user_id);
    List<Maintenance> findByAssetId(Long asset_id);
}
