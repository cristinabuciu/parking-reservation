package com.acs.parking.service;

import com.acs.parking.exception.AuthorizationException;
import com.acs.parking.exception.EntityNotFoundException;
import com.acs.parking.model.OpenSessionsEntity;
import com.acs.parking.model.ParkingSpaceEntity;
import com.acs.parking.model.TransactionsEntity;
import com.acs.parking.repository.OpenSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class OpenSessionsService {
    private final OpenSessionsRepository openSessionsRepository;
    private final TransactionsService transactionsService;
    private final ParkingSpaceService parkingSpaceService;

    @Autowired
    public OpenSessionsService(OpenSessionsRepository openSessionsRepository,
                               TransactionsService transactionsService,
                               ParkingSpaceService parkingSpaceService) {
        this.openSessionsRepository = openSessionsRepository;
        this.transactionsService = transactionsService;
        this.parkingSpaceService = parkingSpaceService;
    }

    public OpenSessionsEntity getById(Long id) {
        return openSessionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public OpenSessionsEntity getByUserId(Long userId) {
        return openSessionsRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public OpenSessionsEntity getByParkingId(Long parkingId) {
        return openSessionsRepository.findByParkingId(parkingId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public OpenSessionsEntity createOpenSessionsEntity(OpenSessionsEntity openSessions) {
        return openSessionsRepository.save(openSessions);
    }

    public OpenSessionsEntity update(OpenSessionsEntity openSessions) {
        getById(openSessions.getId());
        return openSessionsRepository.save(openSessions);
    }

    public OpenSessionsEntity close(Long sessionId, Long userId) {
        OpenSessionsEntity openSession = getById(sessionId);
        if (!userId.equals(openSession.getUserId())) {
            throw new AuthorizationException("User not authorized to change current open session");
        }

        TransactionsEntity newTransaction = getOpenSessionSummary(sessionId, userId);
        transactionsService.create(newTransaction);

        openSessionsRepository.delete(openSession);
        return openSession;
    }

    public TransactionsEntity getOpenSessionSummary(Long sessionId, Long userId) {
        OpenSessionsEntity openSession = getById(sessionId);
        if (!userId.equals(openSession.getUserId())) {
            throw new AuthorizationException("User not authorized to change current open session");
        }
        ZonedDateTime arrivalTime = openSession.getArrivalTime();
        ZonedDateTime departureTime = ZonedDateTime.now();
        Long duration = arrivalTime.until(departureTime, MINUTES);

        ParkingSpaceEntity parkingSpace = parkingSpaceService.getById(openSession.getParkingId());
        Long cost;
        switch (parkingSpace.getCostGranularity()) {
            case COST_PER_10MIN:
                cost = parkingSpace.getCost() * (duration / 10 + 1);
                break;
            case COST_PER_20MIN:
                cost = parkingSpace.getCost() * (duration / 20 + 1);
                break;
            case COST_PER_30MIN:
                cost = parkingSpace.getCost() * (duration / 30 + 1);
                break;
            case COST_PER_1H:
                cost = parkingSpace.getCost() * (duration / 60 + 1);
                break;
            case COST_PER_1D:
                cost = parkingSpace.getCost() * (duration / (60 * 24) + 1);
                break;
            default:
                throw new InvalidParameterException("Invalid cost granularity");
        }

        return new TransactionsEntity(
                openSession.getUserId(), openSession.getParkingId(), duration, arrivalTime, departureTime, cost);
    }
}
