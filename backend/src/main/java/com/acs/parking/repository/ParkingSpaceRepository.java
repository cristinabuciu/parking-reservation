package com.acs.parking.repository;

import com.acs.parking.model.ParkingSpaceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends CrudRepository<ParkingSpaceEntity, Long> {
    List<ParkingSpaceEntity> findByAdminId(Long adminId);
}
