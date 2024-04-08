package org.example.controller;

import jakarta.servlet.http.HttpSession;
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
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);

            //ToDo: Validation Checks

            Customer savedCustomer = customerRepository.save(customer);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register customer");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(HttpSession session, @RequestParam String email, @RequestParam String password) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("customerId", customer.getId());

            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
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
            Customer existingCustomer = customerRepository.findById(customerId).orElse(null);

            if (existingCustomer != null) {
                if (updatedCustomer.getName() != null) {
                    existingCustomer.setName(updatedCustomer.getName());
                }
                if (updatedCustomer.getEmail() != null) {
                    existingCustomer.setEmail(updatedCustomer.getEmail());
                }
                if (updatedCustomer.getPassword() != null) {
                    existingCustomer.setPassword(updatedCustomer.getPassword());
                }
                Customer savedCustomer = customerRepository.save(existingCustomer);
                return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update customer");
        }
    }
}
