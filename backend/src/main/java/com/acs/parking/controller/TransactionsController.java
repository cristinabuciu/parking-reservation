package com.acs.parking.controller;

import com.acs.parking.dto.TimeInterval;
import com.acs.parking.model.TransactionsEntity;
import com.acs.parking.model.UserEntity;
import com.acs.parking.service.TransactionsService;
import com.acs.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final UserService userService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService, UserService userService) {
        this.transactionsService = transactionsService;
        this.userService = userService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionsEntity> getById(@PathVariable Long transactionId) {
        return new ResponseEntity<>(transactionsService.getById(transactionId), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<List<TransactionsEntity>> getByUserId(@PathVariable Long userId,
                                                                @RequestBody TimeInterval timeInterval) {
        return new ResponseEntity<>(transactionsService.getByUserId(
                userId, timeInterval.getStartDate(), timeInterval.getEndDate()), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<List<TransactionsEntity>> getForCurrentUser(Principal principal,
                                                                      @RequestBody TimeInterval timeInterval) {
        UserEntity user = userService.getByUsername(principal.getName());
        return new ResponseEntity<>(transactionsService.getByUserId(
                user.getId(), timeInterval.getStartDate(), timeInterval.getEndDate()), HttpStatus.OK);
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<List<TransactionsEntity>> getByParkingId(@PathVariable Long parkingId) {
        return new ResponseEntity<>(transactionsService.getByParkingId(parkingId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionsEntity> createSession(@RequestBody TransactionsEntity parkingSpaceEntity) {
        return new ResponseEntity<>(transactionsService.create(parkingSpaceEntity), HttpStatus.OK);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionsEntity> updateSession(@PathVariable Long transactionId,
                                                            @RequestBody TransactionsEntity parkingSpaceEntity) {
        parkingSpaceEntity.setId(transactionId);
        return new ResponseEntity<>(transactionsService.update(parkingSpaceEntity), HttpStatus.OK);
    }


}
