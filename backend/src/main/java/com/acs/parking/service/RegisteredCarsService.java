package com.acs.parking.service;

import com.acs.parking.exception.EntityNotFoundException;
import com.acs.parking.model.RegisteredCarsEntity;
import com.acs.parking.repository.RegisteredCarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredCarsService {

    private final RegisteredCarsRepository registeredCarsRepository;

    @Autowired
    public RegisteredCarsService(RegisteredCarsRepository registeredCarsRepository) {
        this.registeredCarsRepository = registeredCarsRepository;
    }

    public RegisteredCarsEntity getById(Long id) {
        return registeredCarsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not registered"));
    }

    public List<RegisteredCarsEntity> getByUserId(Long userId) {
        return registeredCarsRepository.findByUserId(userId);
    }

    public RegisteredCarsEntity createRegisteredCarsEntity(RegisteredCarsEntity registeredCarsEntity) {
        return registeredCarsRepository.save(registeredCarsEntity);
    }

    public RegisteredCarsEntity update(RegisteredCarsEntity registeredCarsEntity) {
        getById(registeredCarsEntity.getId());
        return registeredCarsRepository.save(registeredCarsEntity);
    }

}
