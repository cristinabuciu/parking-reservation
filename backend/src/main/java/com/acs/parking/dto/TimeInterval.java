package com.acs.parking.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TimeInterval {
    private LocalDate startDate;
    private LocalDate endDate;
}
