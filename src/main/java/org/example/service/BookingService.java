package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookingRequestDto;
import org.example.dto.CustomerBookingsResponseDto;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public void bookCar(BookingRequestDto bookingRequestDto) {
        String email = getCurrentUserEmail();
        Order order = bookingRequestDtoToOrder(bookingRequestDto, email);

        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<CustomerBookingsResponseDto> getAllBookingsForCurrentUser() {
        String email = getCurrentUserEmail();
        List<Order> customerOrders = orderRepository.findByCustomerEmail(email);

        return customerOrders.stream()
                .map(this::mapOrderToCustomerBookingsResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBookingForCurrentUser(long orderId) {
        String currentUserEmail = getCurrentUserEmail();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getCustomer().getEmail().equals(currentUserEmail)) {
            throw new IllegalStateException("Cannot delete booking for a different user");
        }

        orderRepository.deleteById(orderId);
    }

    private CustomerBookingsResponseDto mapOrderToCustomerBookingsResponseDto(Order order) {
        Car car = order.getCar();

        return new CustomerBookingsResponseDto(
                car.getBrandName(),
                order.getOrderId(),
                car.getModelName(),
                car.getNumberOfSeats(),
                car.getPrice(),
                order.getPickUpDate(),
                order.getBringBackDate(),
                order.getOrderDate()
        );
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