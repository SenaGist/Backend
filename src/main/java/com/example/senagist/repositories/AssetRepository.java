package com.example.senagist.repositories;

import com.example.senagist.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByInventoryNumber(String inventoryNumber);
}
