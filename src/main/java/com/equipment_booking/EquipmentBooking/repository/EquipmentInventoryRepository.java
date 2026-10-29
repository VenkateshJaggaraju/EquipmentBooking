package com.equipment_booking.EquipmentBooking.repository;

import com.equipment_booking.EquipmentBooking.model.EquipmentInventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface EquipmentInventoryRepository extends JpaRepository<EquipmentInventory, Long>
{
    //insert a record for a booking date with a capacity of five laptops, but do nothing if that date already exists
    @Modifying
    @Query(value = """
        INSERT INTO equipment_inventory
            (booking_date, total_quantity)
        VALUES (:bookingDate, 5)
        ON CONFLICT (booking_date) DO NOTHING
        """, nativeQuery = true)
    void createInventoryIfMissing(
            @Param("bookingDate") LocalDate bookingDate
    );


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT i
        FROM EquipmentInventory i
        WHERE i.bookingDate = :bookingDate
    """)
    Optional<EquipmentInventory> findByDateForUpdate(
            @Param("bookingDate") LocalDate bookingDate
    );
}
/*
 * Pessimistic locking (Good fit here)
        - Locks the inventory row before checking and updating availability.
          "Another transaction that needs the same row must wait until the lock is released."
        - Best when conflicting updates are likely and overbooking must be prevented.


 * Optimistic locking
        - Uses a version field, usually with @Version, to detect conflicting updates.
        - "A transaction may fail when another transaction changes the same record, requiring retry
          or conflict handling."
        - Best when conflicts are relatively uncommon.
 */
