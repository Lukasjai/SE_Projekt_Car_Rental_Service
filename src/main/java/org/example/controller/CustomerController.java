package org.example.controller;

import org.example.Customer;
import org.example.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        try {
            // Create a new Customer object
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);

            // Perform validation checks on customer data
            // For example, check if email is unique, validate fields, etc.

            // Save the customer to the database
            Customer savedCustomer = customerRepository.save(customer);

            // Return success response with saved customer details
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (Exception e) {
            // Return error response if registration fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register customer");
        }
    }
}
