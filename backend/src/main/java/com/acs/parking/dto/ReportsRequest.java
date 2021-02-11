package com.acs.parking.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportsRequest {
    Long parkingId;
    LocalDate startDate;
    LocalDate endDate;
}
