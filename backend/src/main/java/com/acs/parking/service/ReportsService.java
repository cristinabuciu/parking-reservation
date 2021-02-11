package com.acs.parking.service;

import com.acs.parking.model.TransactionsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportsService {
    private TransactionsService transactionsService;

    @Autowired
    public ReportsService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public List<TransactionsEntity> getTransactionByParkingIdAndTimeInterval(Long parkingID, LocalDate startDate, LocalDate endDate) {
        ZonedDateTime zonedStartDate = startDate.atTime(0,0,0,0).atZone(ZoneId.of("UTC+3"));
        ZonedDateTime zonedEndDate = endDate.atTime(23, 59, 59, 999999999).atZone(ZoneId.of("UTC+3"));
        return transactionsService.getByIdAndTimeInterval(parkingID,zonedStartDate , zonedEndDate);
    }

    public Long getRevenue(Long parkingID, LocalDate startDate, LocalDate endDate) {
        List<TransactionsEntity> transactions = getTransactionByParkingIdAndTimeInterval(parkingID, startDate, endDate);
        Long revenue = 0L;
        for (TransactionsEntity transaction : transactions) {
            revenue += transaction.getCost();
        }
        return revenue;
    }

    public int getDistinctUsers(Long parkingID, LocalDate startDate, LocalDate endDate) {
        List<TransactionsEntity> transactions = getTransactionByParkingIdAndTimeInterval(parkingID, startDate, endDate);
        Set<Long> distinctUsers = new HashSet<>();
        for (TransactionsEntity transaction : transactions) {
            distinctUsers.add(transaction.getUserId());
        }

        return distinctUsers.size();
    }

    public int getRegisteredSessions(Long parkingID, LocalDate startDate, LocalDate endDate) {
        return getTransactionByParkingIdAndTimeInterval(parkingID, startDate, endDate).size();
    }
}
