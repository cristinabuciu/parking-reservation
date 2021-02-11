package com.acs.parking.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OpenSessionsEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "user_id")
    private Long userId;
    @Column(nullable = false, name = "car_id")
    private Long carId;
    @Column(nullable = false, name = "parking_id")
    private Long parkingId;
    @Column(nullable = false, name = "arrival_time")
    private ZonedDateTime arrivalTime;

    public OpenSessionsEntity(Long userId, Long carId, Long parkingId, ZonedDateTime arrivalTime) {
        this.userId = userId;
        this.carId = carId;
        this.parkingId = parkingId;
        this.arrivalTime = arrivalTime;
    }
}