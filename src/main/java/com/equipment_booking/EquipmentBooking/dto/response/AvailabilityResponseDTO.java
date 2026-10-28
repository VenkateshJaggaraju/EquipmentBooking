package com.equipment_booking.EquipmentBooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponseDTO {

    private LocalDate date;
    private Integer totalQuantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
}
