package com.equipment_booking.EquipmentBooking.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotBlank(message = "Instructor name is required")
    @Size(max = 100, message = "Instructor name cannot exceed 100 characters")
    private String instructorName;

    @NotNull(message = "Booking date is required")
    @FutureOrPresent(message = "Booking date must be today or later")
    private LocalDate bookingDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 5, message = "Quantity cannot exceed 5")
    private Integer quantity;

    @NotBlank(message = "Purpose is required")
    private String purpose;
}

/*
 * @FutureOrPresent is a Jakarta Bean Validation annotation used to ensure that a date or time is today
 * or in the future. It rejects dates in the past.
 */