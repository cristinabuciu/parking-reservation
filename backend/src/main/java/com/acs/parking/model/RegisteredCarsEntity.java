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
public class RegisteredCarsEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String licencePlate;

    public RegisteredCarsEntity(Long userId, String licencePlate) {
        this.userId = userId;
        this.licencePlate = licencePlate;
    }
}