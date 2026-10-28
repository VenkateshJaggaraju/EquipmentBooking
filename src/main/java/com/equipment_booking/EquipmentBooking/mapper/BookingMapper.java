package com.equipment_booking.EquipmentBooking.mapper;

import com.equipment_booking.EquipmentBooking.dto.request.BookingRequestDTO;
import com.equipment_booking.EquipmentBooking.dto.response.BookingResponseDTO;
import com.equipment_booking.EquipmentBooking.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // Entity <- DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Booking toEntity(BookingRequestDTO requestDTO);

    // DTO <- Entity
    BookingResponseDTO toResponseDTO(Booking booking);
}
