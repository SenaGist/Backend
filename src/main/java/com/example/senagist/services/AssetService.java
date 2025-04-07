package com.example.senagist.services;

import com.example.senagist.models.Asset;
import com.example.senagist.models.GeneralEquipment;
import com.example.senagist.models.LightingEquipment;
import com.example.senagist.models.RefrigerationEquipment;
import com.example.senagist.repositories.AssetRepository;
import com.example.senagist.repositories.GeneralEquipmentRepository;
import com.example.senagist.repositories.LightingEquipmentRepository;
import com.example.senagist.repositories.RefrigerationEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private GeneralEquipmentRepository generalEquipmentRepository;
    @Autowired
    private RefrigerationEquipmentRepository refrigerationEquipmentRepository;
    @Autowired
    private LightingEquipmentRepository lightingEquipmentRepository;

    public List<Asset> getAll(){
        return assetRepository.findAll();
    }

    public Optional<Asset> getById(Long id){
        return assetRepository.findById(id);
    }

    public GeneralEquipment saveGeneralEquipment(GeneralEquipment generalEquipment){
        return generalEquipmentRepository.save(generalEquipment);
    }

    public List<GeneralEquipment> getAllGeneralEquipment(){
        return generalEquipmentRepository.findAll();
    }

    public List<RefrigerationEquipment> getAllRefrigerationEquipment(){
        return refrigerationEquipmentRepository.findAll();
    }

    public RefrigerationEquipment saveRefrigerationEquipment(RefrigerationEquipment refrigerationEquipment){
        return refrigerationEquipmentRepository.save(refrigerationEquipment);
    }

    public List<LightingEquipment> getAllLightingEquipment(){
        return lightingEquipmentRepository.findAll();
    }

    public LightingEquipment saveLightingEquipment(LightingEquipment lightingEquipment){
        return lightingEquipmentRepository.save(lightingEquipment);
    }


}
