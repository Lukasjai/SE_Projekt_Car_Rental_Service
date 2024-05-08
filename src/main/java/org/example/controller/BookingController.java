package org.example.controller;

import org.example.dto.BookingRequestDto;
import org.example.dto.CustomerBookingsResponseDto;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.currencyConverter.CurrencyConverter;


import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> bookCar(@RequestBody BookingRequestDto bookingRequestDto) {
        bookingService.bookCar(bookingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking successful");
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUserBookings(@RequestParam String currency) {
        List<CustomerBookingsResponseDto> orders = bookingService.getAllBookingsForCurrentUser();
        CurrencyConverter currencyConverter = new CurrencyConverter();

        for (CustomerBookingsResponseDto customerBookingsResponseDto : orders) {
            double convertedPrice = currencyConverter.convertCurrency(customerBookingsResponseDto.getCarPrice(), "USD", currency);
            customerBookingsResponseDto.setCarPrice((float) convertedPrice);
        }

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteBooking(@PathVariable("orderId") long orderId) {
        bookingService.deleteBookingForCurrentUser(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Booking deleted successfully");
    }
}
