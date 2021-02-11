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
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "user_id")
    private Long userId;
    @Column(nullable = false, name = "parking_id")
    private Long parkingId;
    @Column(nullable = false)
    private Long duration;
    @Column(nullable = false, name = "arrival_time")
    private ZonedDateTime arrivalTime;
    @Column(nullable = false, name = "departure_time")
    private ZonedDateTime departureTime;
    private Long cost;

    public TransactionsEntity(Long userId, Long parkingId, Long duration, ZonedDateTime arrivalTime, ZonedDateTime departureTime, Long cost) {
        this.userId = userId;
        this.parkingId = parkingId;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.cost = cost;
    }
}