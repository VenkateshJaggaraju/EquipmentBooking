package com.equipment_booking.EquipmentBooking.service.impl;

import com.equipment_booking.EquipmentBooking.dto.request.BookingRequestDTO;
import com.equipment_booking.EquipmentBooking.dto.response.AvailabilityResponseDTO;
import com.equipment_booking.EquipmentBooking.dto.response.BookingResponseDTO;
import com.equipment_booking.EquipmentBooking.mapper.BookingMapper;
import com.equipment_booking.EquipmentBooking.model.Booking;
import com.equipment_booking.EquipmentBooking.model.EquipmentInventory;
import com.equipment_booking.EquipmentBooking.repository.BookingRepository;
import com.equipment_booking.EquipmentBooking.repository.EquipmentInventoryRepository;
import com.equipment_booking.EquipmentBooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class BookingImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EquipmentInventoryRepository inventoryRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Transactional // ACID
    public ResponseEntity<BookingResponseDTO> createBooking(BookingRequestDTO requestDTO) {
        LocalDate date = requestDTO.getBookingDate();

        // Ensure that an inventory row exists for this date.
        inventoryRepository.createInventoryIfMissing(date);

        // Lock the inventory row before checking availability.
        EquipmentInventory inventory = inventoryRepository
                .findByDateForUpdate(date)
                .orElseThrow(() -> new IllegalStateException(
                        "Inventory not found for date: " + date
                ));

        // Calculate the number of laptops already reserved.
        long reserved =
                bookingRepository.getReservedQuantityByDate(date);

        int available =
                inventory.getTotalQuantity() - (int) reserved;

        // Reject requests that exceed the remaining capacity.
        if (requestDTO.getQuantity() > available) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Insufficient equipment availability. Available: "
                            + available
            );
        }

        // Accept only ifn't exceeded
        Booking booking = bookingMapper.toEntity(requestDTO);

        // Save the booking. @PrePersist sets status and createdAt.
        Booking savedBooking = bookingRepository.save(booking);

        // Convert the saved entity to the response DTO.
        BookingResponseDTO response= bookingMapper.toResponseDTO(savedBooking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BookingResponseDTO> getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Booking not found with id: " + id
                ));

        BookingResponseDTO response = bookingMapper.toResponseDTO(booking);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<AvailabilityResponseDTO> getAvailability(LocalDate date) {
        inventoryRepository.createInventoryIfMissing(date);

        EquipmentInventory inventory = inventoryRepository
                .findByDateForUpdate(date)
                .orElseThrow(() -> new IllegalStateException(
                        "Inventory not found for date: " + date
                ));

        long reserved =
                bookingRepository.getReservedQuantityByDate(date);

        int available =
                inventory.getTotalQuantity() - (int) reserved;

        AvailabilityResponseDTO response = new AvailabilityResponseDTO(
                date,
                inventory.getTotalQuantity(),
                (int) reserved,
                available
        );
        
        return ResponseEntity.ok(response);
    }
}
