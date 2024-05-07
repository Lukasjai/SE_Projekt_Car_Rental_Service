package org.example.service;

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
    public Customer registerCustomer(CustomerDto customerDto) {
        if (customerRepository.findByEmail(customerDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        Customer customer = convertCustomerDtoToEntity(customerDto);

        return customerRepository.save(customer);
    }

    public void updateCustomerInfo(String email, CustomerDto customerDto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customerDto.getPhoneNumber() != null && customerDto.getPhoneNumber().length() <= 15) {
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Invalid phone number length");
        }
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
                .orElseThrow(null);

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
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setLicenceNumber(customer.getLicenceNumber());

        return customerDto;
    }

    private Customer convertCustomerDtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
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
