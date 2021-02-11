package com.acs.parking.service;

import com.acs.parking.exception.EntityNotFoundException;
import com.acs.parking.model.TransactionsEntity;
import com.acs.parking.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.time.ZonedDateTime;

@Service
public class TransactionsService {

    public final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsService( TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public TransactionsEntity getById(Long id) {
        return transactionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
    }

    public List<TransactionsEntity> getByUserId(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionsRepository.findByUserIdAndDepartureTimeBetween(userId,
                startDate.atTime(0, 0, 0, 0).atZone(ZoneId.of("UTC+3")),
                endDate.atTime(23, 59, 59, 999999999).atZone(ZoneId.of("UTC+3")));
    }

    public List<TransactionsEntity> getByParkingId(Long parkingId) {
        return transactionsRepository.findByParkingId(parkingId);
    }

    public TransactionsEntity create(TransactionsEntity transactionsEntity) {
        return transactionsRepository.save(transactionsEntity);
    }

    public TransactionsEntity update(TransactionsEntity transactionsEntity) {
        getById(transactionsEntity.getId());
        return transactionsRepository.save(transactionsEntity);
    }

    public List<TransactionsEntity> getByIdAndTimeInterval(Long parkingId, ZonedDateTime arrivalTime, ZonedDateTime departureTime) {
        return transactionsRepository.findByParkingIdAndDepartureTimeBetween(parkingId, arrivalTime, departureTime);
    }

}
