package com.acs.parking.repository;
import com.acs.parking.model.OpenSessionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpenSessionsRepository extends CrudRepository<OpenSessionsEntity, Long> {
    Optional<OpenSessionsEntity> findByUserId(Long userID);
    Optional<OpenSessionsEntity> findByParkingId(Long parkingId);
}

