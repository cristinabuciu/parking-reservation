package com.acs.parking.controller;

import com.acs.parking.model.OpenSessionsEntity;
import com.acs.parking.model.ParkingSpaceEntity;
import com.acs.parking.model.TransactionsEntity;
import com.acs.parking.model.UserEntity;
import com.acs.parking.service.OpenSessionsService;
import com.acs.parking.service.ParkingSpaceService;
import com.acs.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/open/sessions")
public class OpenSessionsController {
    private final OpenSessionsService openSessionsService;
    private final UserService userService;
    private final ParkingSpaceService parkingSpaceService;

    @Autowired
    public OpenSessionsController(OpenSessionsService openSessionsService, UserService userService, ParkingSpaceService parkingSpaceService) {
        this.openSessionsService = openSessionsService;
        this.userService = userService;
        this.parkingSpaceService = parkingSpaceService;
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<OpenSessionsEntity> getById(@PathVariable Long sessionId) {
        return new ResponseEntity<>(openSessionsService.getById(sessionId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<OpenSessionsEntity> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(openSessionsService.getByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<OpenSessionsEntity> getByParkingId(@PathVariable Long parkingId) {
        return new ResponseEntity<>(openSessionsService.getByParkingId(parkingId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OpenSessionsEntity> createSession(Principal principal,
                                                            @RequestBody OpenSessionsEntity openSessionsEntity) {
        ParkingSpaceEntity parkingSpaceEntity = parkingSpaceService.getById(openSessionsEntity.getParkingId());
        parkingSpaceEntity.setFreeParkingSpaces(parkingSpaceEntity.getFreeParkingSpaces() - 1);
        parkingSpaceService.update(parkingSpaceEntity);
        UserEntity user = userService.getByUsername(principal.getName());
        openSessionsEntity.setUserId(user.getId());
        openSessionsEntity.setArrivalTime(ZonedDateTime.now());
        return new ResponseEntity<>(openSessionsService.createOpenSessionsEntity(openSessionsEntity), HttpStatus.OK);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<OpenSessionsEntity> updateSession(@PathVariable Long sessionId,
                                                            @RequestBody OpenSessionsEntity openSessionsEntity) {
        openSessionsEntity.setId(sessionId);
        return new ResponseEntity<>(openSessionsService.update(openSessionsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/close/{sessionId}")
    public ResponseEntity<OpenSessionsEntity> closeSession(Principal principal, @PathVariable Long sessionId) {
        OpenSessionsEntity openSessionsEntity = openSessionsService.getById(sessionId);
        ParkingSpaceEntity parkingSpaceEntity = parkingSpaceService.getById(openSessionsEntity.getParkingId());
        parkingSpaceEntity.setFreeParkingSpaces(parkingSpaceEntity.getFreeParkingSpaces() + 1);
        parkingSpaceService.update(parkingSpaceEntity);
        UserEntity user = userService.getByUsername(principal.getName());
        return new ResponseEntity<>(openSessionsService.close(sessionId, user.getId()), HttpStatus.OK);
    }

    @GetMapping("/summary/{sessionId}")
    public ResponseEntity<TransactionsEntity> getSummary(Principal principal, @PathVariable Long sessionId) {
        UserEntity user = userService.getByUsername(principal.getName());
        return new ResponseEntity<>(openSessionsService.getOpenSessionSummary(sessionId, user.getId()), HttpStatus.OK);
    }


}
