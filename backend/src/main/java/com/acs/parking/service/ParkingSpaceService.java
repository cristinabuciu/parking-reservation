package com.acs.parking.service;

import com.acs.parking.exception.EntityNotFoundException;
import com.acs.parking.model.ParkingSpaceEntity;
import com.acs.parking.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public ParkingSpaceEntity getById(Long id) {
        return parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parking space not found"));
    }

    public List<ParkingSpaceEntity> getAll() {
        List<ParkingSpaceEntity> parkingSpaces = new ArrayList<>();
        parkingSpaceRepository.findAll().forEach(parkingSpaces::add);
        return parkingSpaces;
    }

    public List<ParkingSpaceEntity> getByAdminId(Long userId) {
        return parkingSpaceRepository.findByAdminId(userId);
    }

    public ParkingSpaceEntity createParkingSpaceEntity(ParkingSpaceEntity parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    public ParkingSpaceEntity update(ParkingSpaceEntity parkingSpace) {
        getById(parkingSpace.getId());
        return parkingSpaceRepository.save(parkingSpace);
    }
}
