package com.example.senagist.repositories;

import com.example.senagist.models.LightingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightingEquipmentRepository extends JpaRepository<LightingEquipment, Long> {
}
