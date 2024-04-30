package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookingRequestDto;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.service.BookingService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private BookingController bookingController;

    private BookingRequestDto bookingRequestDto;


    @BeforeEach
    void setUp() {
        LocalDate bookDate = LocalDate.now(Clock.systemUTC());
        LocalDate returnDate = LocalDate.now(ZoneOffset.ofHours(+72));
        LocalDate orderDate = bookDate;
        bookingRequestDto = new BookingRequestDto(1, bookDate, returnDate, orderDate, 30);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bookCarHappyPath() {
        when(bookingService.bookCar(any(BookingRequestDto.class))).thenAnswer(invocation ->
                ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully"));
        bookingService.bookCar(bookingRequestDto);
    }

    @Test
    void getCurrentUserBookings() {
    }

    @Test
    void deleteBooking() {
    }
}