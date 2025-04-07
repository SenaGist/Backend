package com.example.senagist.repositories;

import com.example.senagist.models.GeneralEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralEquipmentRepository extends JpaRepository<GeneralEquipment, Long> {
}
