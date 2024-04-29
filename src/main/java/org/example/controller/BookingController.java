package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookingRequestDto;
import org.example.dto.CustomerBookingsResponseDto;
import org.example.model.Car;
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
        try {
            bookingService.bookCar(bookingRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking successful");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, please try again");
        }
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUserBookings(@RequestParam String currency) {
        try {
            List<CustomerBookingsResponseDto> orders = bookingService.getAllBookingsForCurrentUser();
            CurrencyConverter currencyConverter = new CurrencyConverter();

            for (CustomerBookingsResponseDto customerBookingsResponseDto : orders) {
                System.out.println("update PRice");
                double convertedPrice = currencyConverter.convertCurrency(customerBookingsResponseDto.getCarPrice(), "USD", currency);
                customerBookingsResponseDto.setCarPrice((float) convertedPrice);
            }

            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, please try again");
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteBooking(@PathVariable("orderId") long orderId) {
        try {
            bookingService.deleteBookingForCurrentUser(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Booking deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, please try again");
        }
    }
}
