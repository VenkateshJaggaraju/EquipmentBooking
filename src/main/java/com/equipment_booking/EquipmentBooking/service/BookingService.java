package com.equipment_booking.EquipmentBooking.service;

import com.equipment_booking.EquipmentBooking.dto.request.BookingRequestDTO;
import com.equipment_booking.EquipmentBooking.dto.response.AvailabilityResponseDTO;
import com.equipment_booking.EquipmentBooking.dto.response.BookingResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface BookingService {

    ResponseEntity<BookingResponseDTO> createBooking(@Valid BookingRequestDTO requestDTO);

    ResponseEntity<BookingResponseDTO> getBookingById(Long id);

    ResponseEntity<AvailabilityResponseDTO> getAvailability(@NotNull(message = "Date is required")
                                @FutureOrPresent(message = "Date must be today or later") LocalDate date
    );
}
