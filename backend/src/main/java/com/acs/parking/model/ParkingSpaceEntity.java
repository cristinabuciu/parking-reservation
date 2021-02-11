package com.acs.parking.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ParkingSpaceEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "admin_id")
    private Long adminId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, name = "free_parking_spaces")
    private Long freeParkingSpaces;
    @Column(nullable = false, name = "total_parking_spaces")
    private Long totalParkingSpaces;
    @Column(nullable = false)
    private Long cost;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cost_granularity")
    private CostGranularity costGranularity;

    public ParkingSpaceEntity(Long adminId, String name, String address, Long freeParkingSpaces, Long totalParkingSpaces, Long cost,
                              CostGranularity costGranularity) {
        this.adminId = adminId;
        this.name = name;
        this.address = address;
        this.freeParkingSpaces = freeParkingSpaces;
        this.totalParkingSpaces = totalParkingSpaces;
        this.cost = cost;
        this. costGranularity = costGranularity;
    }
}
