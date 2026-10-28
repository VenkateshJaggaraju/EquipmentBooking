package com.equipment_booking.EquipmentBooking.dto.response;

import com.equipment_booking.EquipmentBooking.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {

    private Long id;
    private String instructorName;
    private LocalDate bookingDate;
    private Integer quantity;
    private String purpose;
    private BookingStatus status;
    private LocalDateTime createdAt;
}
