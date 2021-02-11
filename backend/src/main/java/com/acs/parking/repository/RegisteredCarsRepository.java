package com.acs.parking.repository;
import com.acs.parking.model.RegisteredCarsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredCarsRepository extends CrudRepository<RegisteredCarsEntity, Long> {
    List<RegisteredCarsEntity> findByUserId(Long userId);
}
