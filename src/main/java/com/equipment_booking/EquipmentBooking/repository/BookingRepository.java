package com.equipment_booking.EquipmentBooking.repository;


import com.equipment_booking.EquipmentBooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT COALESCE(SUM(b.quantity), 0)
        FROM Booking b
        WHERE b.bookingDate = :bookingDate
    """)
    Long getReservedQuantityByDate(
            @Param("bookingDate") LocalDate bookingDate
    );

/*
 * COALESCE returns the first value that isn't NULL.
 * If there are no bookings for the requested date, SUM() returns NULL. COALESCE converts that result to 0.
 */
}

