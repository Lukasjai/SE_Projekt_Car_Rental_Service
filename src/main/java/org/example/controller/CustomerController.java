package org.example.controller;

import org.example.Customer;
import org.example.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestParam String email, @RequestParam String password) {
        // Find the customer by email
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            // Return success response with customer details
            return ResponseEntity.ok(customer);
        } else {
            // Return error response if login fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/id?email=")
    public ResponseEntity<Long> findCustomerIdByEmail(@RequestParam String email) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findByEmail(email));
        if (customerOptional.isPresent()) {
            return ResponseEntity.ok(customerOptional.get().getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        try {
            // Find the existing customer by their ID
            Customer existingCustomer = customerRepository.findById(customerId).orElse(null);

            if (existingCustomer != null) {
                // Update only the provided fields
                if (updatedCustomer.getName() != null) {
                    existingCustomer.setName(updatedCustomer.getName());
                }
                if (updatedCustomer.getEmail() != null) {
                    existingCustomer.setEmail(updatedCustomer.getEmail());
                }
                if (updatedCustomer.getPassword() != null) {
                    existingCustomer.setPassword(updatedCustomer.getPassword());
                }

                // Save the updated customer to the database
                Customer savedCustomer = customerRepository.save(existingCustomer);

                // Return success response with updated customer details
                return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
            } else {
                // If the customer with the given ID does not exist, return 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            // Return error response if update fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update customer");
        }
    }
}
