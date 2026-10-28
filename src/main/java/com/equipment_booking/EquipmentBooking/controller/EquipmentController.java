package com.equipment_booking.EquipmentBooking.controller;

import com.equipment_booking.EquipmentBooking.dto.response.AvailabilityResponseDTO;
import com.equipment_booking.EquipmentBooking.service.BookingService;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/availability")
    public ResponseEntity<AvailabilityResponseDTO> getAvailability(
            @RequestParam
            @NotNull(message = "Date is required")
            @FutureOrPresent(message = "Date must be today or later")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return bookingService.getAvailability(date);
    }
}
