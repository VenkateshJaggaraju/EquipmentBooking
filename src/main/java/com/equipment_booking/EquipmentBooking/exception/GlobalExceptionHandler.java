package com.equipment_booking.EquipmentBooking.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String getMessage(Exception exception){
        return exception.getMessage();
    }
}
