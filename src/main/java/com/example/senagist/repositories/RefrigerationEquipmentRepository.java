package com.example.senagist.repositories;

import com.example.senagist.models.RefrigerationEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefrigerationEquipmentRepository extends JpaRepository<RefrigerationEquipment, Long> {

}
