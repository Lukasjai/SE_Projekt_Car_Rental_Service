package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookingRequestDto;
import org.example.dto.CustomerBookingsResponseDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private BookingController bookingController;

    private BookingRequestDto bookingRequestDto;
    private CustomerBookingsResponseDto customerBookingsResponseDto;


    @BeforeEach
    void setUp() {
        LocalDate bookDate = LocalDate.now(Clock.systemUTC());
        LocalDate returnDate = LocalDate.now(ZoneOffset.ofHours(+12));
        LocalDate orderDate = bookDate;
        bookingRequestDto = new BookingRequestDto(1, bookDate, returnDate, orderDate, 30);
        customerBookingsResponseDto = new CustomerBookingsResponseDto("Fiat", 1, "Punto", 4, 30, returnDate, orderDate, bookDate);
    }

    @AfterEach
    void tearDown() {
    }

/*    @Test
    void bookCarHappyPath() {
        when(bookingService.bookCar(any(BookingRequestDto.class))).thenAnswer(invocation -> ResponseEntity.status(HttpStatus.CREATED).body("Booking successful"));

        ResponseEntity<?> responseEntity = bookingController.bookCar(bookingRequestDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Booking successful", Objects.requireNonNull(responseEntity.getBody()));
    }*/

    @Test
    void getCurrentUserBookings() {
        BookingService bookingService = mock(BookingService.class);

        List<CustomerBookingsResponseDto> bookings = new ArrayList<>();
        bookings.add(customerBookingsResponseDto);

        when(bookingService.getAllBookingsForCurrentUser()).thenReturn(bookings);
        BookingController bookingController = new BookingController(bookingService);
        ResponseEntity<?> responseEntity = bookingController.getCurrentUserBookings("EUR");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //TODO: find out what the original method is supposed to do and adapt test

    }

    @Test
    void deleteBooking() {
        BookingService bookingService = mock(BookingService.class);
        doNothing().when(bookingService).deleteBookingForCurrentUser(anyLong());
        BookingController bookingController = new BookingController(bookingService);
        ResponseEntity<?> responseEntity = bookingController.deleteBooking(12345L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Booking deleted successfully", responseEntity.getBody());
    }
}