package org.example.service;

import org.example.dto.CustomerLoginDto;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.dto.CustomerDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public CustomerService(CustomerRepository customerRepository, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Customer registerCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setLicenceNumber(customerDto.getLicenceNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(this.passwordEncoder.encode(customerDto.getPassword()));

        return customerRepository.save(customer);
    }

    public Customer authenticateCustomer(CustomerLoginDto customerLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerLoginDto.getEmail(),
                        customerLoginDto.getPassword()
                )
        );

        return customerRepository.findByEmail(customerLoginDto.getEmail()).orElse(null);
    }
}
