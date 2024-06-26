package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.CustomerLoginDto;
import org.example.exception.EmailAlreadyInUseException;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.dto.CustomerDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void registerCustomer(CustomerDto customerDto) {
        if (customerRepository.findByEmail(customerDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        Customer customer = convertCustomerDtoToEntity(customerDto);

        customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomerPhoneNumber(CustomerDto customerDto) {
        String email = getCurrentUserEmail();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customer.setPhoneNumber(String.valueOf(customerDto.getPhoneNumber()));
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomerByEmail() {
        String email = getCurrentUserEmail();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    public CustomerDto getCustomerInfo(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return convertCustomerToDto(customer);
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

    private CustomerDto convertCustomerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmail(customer.getEmail());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhoneNumber(Long.parseLong(String.valueOf(customer.getPhoneNumber())));
        customerDto.setLicenceNumber(customer.getLicenceNumber());

        return customerDto;
    }

    private Customer convertCustomerDtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(String.valueOf(customerDto.getPhoneNumber()));
        customer.setLicenceNumber(customerDto.getLicenceNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(this.passwordEncoder.encode(customerDto.getPassword()));

        return customer;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
