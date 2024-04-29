package org.example.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.CustomerDto;
import org.example.dto.CustomerLoginDto;
import org.example.dto.LoginResponseDto;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.example.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CustomerController customerController;


 /*   @Test
    void registerCustomer() {

        CustomerDto customerDto = new CustomerDto("Tilly", "Bohl", "068112345678", 12345,"tilly@tilly.com","1234abc");
        when(customerService.registerCustomer(customerDto)).thenAnswer(invocation ->
                ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully"));
        ResponseEntity<?> responseEntity = customerController.registerCustomer(customerDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Customer registered successfully", Objects.requireNonNull(responseEntity.getBody()));

        verify(customerService, times(1)).registerCustomer(customerDto);
    }


//any(CustomerDto.class))*/


    @Test
    void loginCustomerCredentialsValid() {
        CustomerLoginDto loginDto = new CustomerLoginDto("validEmail", "validPassword");
        Customer loginCustomer = new Customer();
        String jwtToken = "mockJwtToken";
        when(customerService.authenticateCustomer(loginDto)).thenReturn(loginCustomer);
        when(jwtService.generateToken(loginCustomer)).thenReturn(jwtToken);
        ResponseEntity<?> responseEntity = customerController.loginCustomer(loginDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LoginResponseDto responseBody = (LoginResponseDto) responseEntity.getBody();
        assertEquals(jwtToken, responseBody.getJwtToken());
        verify(httpServletResponse, times(1)).addCookie(any());
        verify(customerService, times(1)).authenticateCustomer(loginDto);
        verify(jwtService, times(1)).generateToken(loginCustomer);
    }
    @Test
    void loginCustomerCredentialsInvalid() {
        CustomerLoginDto loginDto = new CustomerLoginDto("invalidEmail", "invalidPassword");
        when(customerService.authenticateCustomer(loginDto)).thenThrow(BadCredentialsException.class);
        ResponseEntity<?> responseEntity = customerController.loginCustomer(loginDto);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Invalid credentials", responseEntity.getBody());
        verify(customerService, times(1)).authenticateCustomer(loginDto);
    }
    @Test
    void checkCustomerSessionSessionValid() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie[] cookies = {new Cookie("jwtToken", "validToken")};
        when(request.getCookies()).thenReturn(cookies);
        when(jwtService.validateToken("validToken")).thenReturn(true);
        ResponseEntity<?> responseEntity = customerController.checkCustomerSession(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Session is valid.", responseEntity.getBody());
    }
    @Test
    void checkCustomerSessionSessionInvalid() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie[] cookies = {new Cookie("jwtToken", "invalidToken")};
        when(request.getCookies()).thenReturn(cookies);
        when(jwtService.validateToken("invalidToken")).thenReturn(false);
        ResponseEntity<?> responseEntity = customerController.checkCustomerSession(request);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Session is not valid.", responseEntity.getBody());
    }
    @Test
    void logoutCustomerHappyPath() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ResponseEntity<?> responseEntity = customerController.logoutCustomer(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Customer logged out successfully", responseEntity.getBody());
        verify(response, times(1)).addCookie(any(Cookie.class));
    }


}