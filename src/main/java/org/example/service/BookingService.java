package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookingRequestDto;
import org.example.model.Car;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.CarRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public BookingService(OrderRepository orderRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public Order bookCar(BookingRequestDto bookingRequestDto) {
        String email = getCurrentUserEmail();
        Order order = bookingRequestDtoToOrder(bookingRequestDto, email);

        return orderRepository.save(order);
    }

    private Order bookingRequestDtoToOrder(BookingRequestDto bookingRequestDto, String email) {
        Order order = new Order();
        order.setPickUpDate(bookingRequestDto.getPickupDate());
        order.setBringBackDate(bookingRequestDto.getReturnDate());
        order.setCar(getCarForBooking(bookingRequestDto.getCarId()));
        order.setCustomer(getCustomerForBooking(email));
        order.setOrderDate(bookingRequestDto.getOrderDate());
        order.setPrice(bookingRequestDto.getPrice());

        return order;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private Car getCarForBooking(long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    private Customer getCustomerForBooking(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
