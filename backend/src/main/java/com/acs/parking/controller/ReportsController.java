package com.acs.parking.controller;

import com.acs.parking.dto.ReportsRequest;
import com.acs.parking.model.TransactionsEntity;
import com.acs.parking.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {
    private final ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<List<TransactionsEntity>> getTransactionByParkingIdAnTimeInterval(@RequestBody ReportsRequest reportsRequest) {
        return new ResponseEntity<>(reportsService.getTransactionByParkingIdAndTimeInterval(reportsRequest.getParkingId(), reportsRequest.getStartDate(), reportsRequest.getEndDate()), HttpStatus.OK);
    }

    @PostMapping("/revenue")
    public ResponseEntity<Long> getTotalRevenue(@RequestBody ReportsRequest reportsRequest) {
        return new ResponseEntity<>(reportsService.getRevenue(reportsRequest.getParkingId(), reportsRequest.getStartDate(), reportsRequest.getEndDate()), HttpStatus.OK);
    }

    @PostMapping("/distinct/users")
    public ResponseEntity<Integer> getDistinctUsers(@RequestBody ReportsRequest reportsRequest) {
        return new ResponseEntity<>(reportsService.getDistinctUsers(reportsRequest.getParkingId(), reportsRequest.getStartDate(), reportsRequest.getEndDate()), HttpStatus.OK);
    }

    @PostMapping("/sessions")
    public ResponseEntity<Integer> getSessions(@RequestBody ReportsRequest reportsRequest) {
        return new ResponseEntity<>(reportsService.getRegisteredSessions(reportsRequest.getParkingId(), reportsRequest.getStartDate(), reportsRequest.getEndDate()), HttpStatus.OK);
    }


}
