package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.CustomerLoginDto;
import org.example.dto.LoginResponseDto;
import org.example.model.Customer;
import org.example.dto.CustomerDto;
import org.example.service.CustomerService;
import org.example.service.JwtService;
import org.example.util.CookieUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final JwtService jwtService;
    private final CustomerService customerService;
    private final HttpServletResponse httpServletResponse;

    public CustomerController(JwtService jwtService, CustomerService customerService, HttpServletResponse httpServletResponse) {
        this.jwtService = jwtService;
        this.customerService = customerService;
        this.httpServletResponse = httpServletResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDto customerDto) {
        customerService.registerCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerLoginDto loginCustomerDto) {
        Customer loginCustomer = customerService.authenticateCustomer(loginCustomerDto);
        String jwtToken = jwtService.generateToken(loginCustomer);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setJwtToken(jwtToken);
        loginResponseDto.setJwtExpirationInMilliseconds(jwtService.getExpirationTime());

        CookieUtils.setJwtCookie(httpServletResponse, jwtToken, (int)jwtService.getExpirationTime());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutCustomer(HttpServletResponse response) {
        CookieUtils.clearJwtCookie(response);

        return ResponseEntity.ok().body("Customer logged out successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerDto> getProfile(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = CookieUtils.findJwtCookie(cookies);

        if (jwtCookie != null && jwtService.validateToken(jwtCookie.getValue())) {
            CustomerDto customer = customerService.getCustomerInfo(jwtService.extractUsername(jwtCookie.getValue()));
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto) {
        customerService.updateCustomerPhoneNumber(customerDto);
        return ResponseEntity.status(HttpStatus.OK).body("Profile updated successfully.");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(HttpServletResponse response) {
        customerService.deleteCustomerByEmail();
        CookieUtils.clearJwtCookie(response);

        return ResponseEntity.ok().body("Customer profile deleted successfully.");
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkCustomerSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie = CookieUtils.findJwtCookie(cookies);

        if (jwtCookie != null && jwtService.validateToken(jwtCookie.getValue())) {
            return ResponseEntity.ok().body("Session is valid.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session is not valid.");
    }
}