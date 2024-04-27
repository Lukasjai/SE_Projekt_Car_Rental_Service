package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookingRequestDto;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
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
}
