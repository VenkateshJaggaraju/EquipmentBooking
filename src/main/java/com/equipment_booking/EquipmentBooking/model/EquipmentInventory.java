package com.equipment_booking.EquipmentBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "equipment_inventory",
        uniqueConstraints = @UniqueConstraint(name = "uk_inventory_booking_date",
                columnNames = "booking_date")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = false, unique = true)
    private LocalDate bookingDate;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 5;
}
