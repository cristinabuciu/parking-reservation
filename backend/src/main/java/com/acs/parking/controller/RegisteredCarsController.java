package com.acs.parking.controller;

import com.acs.parking.model.RegisteredCarsEntity;
import com.acs.parking.model.UserEntity;
import com.acs.parking.service.RegisteredCarsService;
import com.acs.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/registered/cars")
public class RegisteredCarsController {
    private final RegisteredCarsService registeredCarsService;
    private final UserService userService;

    @Autowired
    public RegisteredCarsController(RegisteredCarsService registeredCarsService, UserService userService) {
        this.registeredCarsService = registeredCarsService;
        this.userService = userService;
    }

    @GetMapping("/{carId}")
    public ResponseEntity<RegisteredCarsEntity> getById(@PathVariable Long carId) {
        return new ResponseEntity<>(registeredCarsService.getById(carId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RegisteredCarsEntity>> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(registeredCarsService.getByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegisteredCarsEntity> createSession(@RequestBody RegisteredCarsEntity registeredCarsEntity,
                                                              Principal principal) {
        UserEntity user = userService.getByUsername(principal.getName());
        registeredCarsEntity.setUserId(user.getId());
        return new ResponseEntity<>(registeredCarsService.createRegisteredCarsEntity(registeredCarsEntity), HttpStatus.OK);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<RegisteredCarsEntity> updateSession(@PathVariable Long carId,
                                                              @RequestBody RegisteredCarsEntity registeredCarsEntity) {
        registeredCarsEntity.setId(carId);
        return new ResponseEntity<>(registeredCarsService.update(registeredCarsEntity), HttpStatus.OK);
    }
}
