package com.acs.parking.controller;

import com.acs.parking.model.ParkingSpaceEntity;
import com.acs.parking.model.UserEntity;
import com.acs.parking.service.ParkingSpaceService;
import com.acs.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/parking/space")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;
    private final UserService userService;

    @Autowired
    public ParkingSpaceController(ParkingSpaceService parkingSpaceService, UserService userService) {
        this.parkingSpaceService = parkingSpaceService;
        this.userService = userService;
    }

    @GetMapping("/{parkingSpaceId}")
    public ResponseEntity<ParkingSpaceEntity> getById(@PathVariable Long parkingSpaceId) {
        return new ResponseEntity<>(parkingSpaceService.getById(parkingSpaceId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpaceEntity>> getAll() {
        return new ResponseEntity<>(parkingSpaceService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ParkingSpaceEntity>> getByAdminId(@PathVariable Long adminId) {
        return new ResponseEntity<>(parkingSpaceService.getByAdminId(adminId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParkingSpaceEntity> registerParkingSpace(@RequestBody ParkingSpaceEntity parkingSpaceEntity,
                                                                   Principal principal) {
        UserEntity user = userService.getByUsername(principal.getName());
        parkingSpaceEntity.setAdminId(user.getId());
        return new ResponseEntity<>(parkingSpaceService.createParkingSpaceEntity(parkingSpaceEntity), HttpStatus.OK);
    }

    @PutMapping("/{parkingSpaceId}")
    public ResponseEntity<ParkingSpaceEntity> updateParkingSpace(@PathVariable Long parkingSpaceId,
                                                                 @RequestBody ParkingSpaceEntity parkingSpaceEntity) {
        parkingSpaceEntity.setId(parkingSpaceId);
        return new ResponseEntity<>(parkingSpaceService.update(parkingSpaceEntity), HttpStatus.OK);
    }
}
