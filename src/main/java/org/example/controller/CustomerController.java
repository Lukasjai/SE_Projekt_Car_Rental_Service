package org.example.controller;

import org.example.dto.CustomerLoginDto;
import org.example.dto.LoginResponseDto;
import org.example.model.Customer;
import org.example.dto.CustomerDto;
import org.example.service.CustomerService;
import org.example.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final JwtService jwtService;
    private final CustomerService customerService;

    public CustomerController(JwtService jwtService, CustomerService customerService) {
        this.jwtService = jwtService;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDto customerDto) {
        try {
            customerService.registerCustomer(customerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerLoginDto loginCustomerDto) {
        Customer loginCustomer = customerService.authenticateCustomer(loginCustomerDto);

        String jwtToken = jwtService.generateToken(loginCustomer);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setJwtToken(jwtToken);
        loginResponseDto.setJwtExpirationInMilliseconds(jwtService.getExpirationTime());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> loginCustomer(HttpSession session, @RequestParam String email, @RequestParam String password) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            session.setAttribute("customerId", customer.getId());

            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }*/

    /*@GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }*/

    /*@GetMapping("/id?email=")
    public ResponseEntity<Long> findCustomerIdByEmail(@RequestParam String email) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findByEmail(email));
        if (customerOptional.isPresent()) {
            return ResponseEntity.ok(customerOptional.get().getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    /*@PatchMapping("/update/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        try {
            Customer existingCustomer = customerRepository.findById(customerId).orElse(null);

            if (existingCustomer != null) {
                if (updatedCustomer.getfirstName() != null) {
                    existingCustomer.setfirstName(updatedCustomer.getfirstName());
                }
                if (updatedCustomer.getlastName() != null) {
                    existingCustomer.setlastName(updatedCustomer.getlastName());
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
    }*/
}
