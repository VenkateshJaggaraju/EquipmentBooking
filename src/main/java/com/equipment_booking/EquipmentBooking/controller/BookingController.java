package com.equipment_booking.EquipmentBooking.controller;

import com.equipment_booking.EquipmentBooking.dto.request.BookingRequestDTO;
import com.equipment_booking.EquipmentBooking.dto.response.BookingResponseDTO;
import com.equipment_booking.EquipmentBooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.createBooking(requestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(
            @PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
}
