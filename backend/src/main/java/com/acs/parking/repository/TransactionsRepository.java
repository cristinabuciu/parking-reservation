package com.acs.parking.repository;

import com.acs.parking.model.TransactionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository extends CrudRepository<TransactionsEntity, Long> {
    List<TransactionsEntity> findByUserIdAndDepartureTimeBetween(
            Long userID, ZonedDateTime startDate,  ZonedDateTime endDate);

    List<TransactionsEntity> findByParkingId(Long parkingId);
    List<TransactionsEntity> findByParkingIdAndDepartureTimeBetween(Long parkingId, ZonedDateTime arrivalTime, ZonedDateTime departureTime);
}
