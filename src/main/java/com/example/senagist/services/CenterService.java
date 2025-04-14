package com.example.senagist.services;

import com.example.senagist.models.Center;
import com.example.senagist.repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;

    public List<Center> getAll(){
        return centerRepository.findAll();
    }
}
