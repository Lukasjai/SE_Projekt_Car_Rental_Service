package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.Customer;
import org.example.CustomerRepository;
import org.example.database.MariaDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private MariaDB databaseConnection = new MariaDB();
    private String customer_firstname;
    private String customer_lastname;
    private String customer_telephone_number = "06601230879";
    private String email;
    private String password;
    private int licence_number = 123456;
    private String insertQuery = "INSERT INTO customers (customer_firstname, customer_lastname, customer_telephone_number, licence_number, password, email) VALUES (?, ?, ?, ?, ?,?)";

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
  


    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestParam String vorname, @RequestParam String nachname, @RequestParam String email, @RequestParam String password) {
        try {
            Customer customer = new Customer();
            customer.setfirstName(vorname);
            customer.setlastName(nachname);
            customer.setEmail(email);
            customer.setPassword(password);
            this.customer_firstname = vorname;
            this.customer_lastname = nachname;
            this.email = email;
            this.customer_telephone_number = password;
            Connection connection = databaseConnection.getConnection();
            insertQuery = "INSERT INTO customers (customer_firstname, customer_lastname, customer_telephone_number, licence_number, password, email) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            //ToDo: Validation Checks
            preparedStatement.setString(1, this.customer_firstname);
            preparedStatement.setString(2, this.customer_lastname);
            preparedStatement.setString(3, this.customer_telephone_number);
            preparedStatement.setInt(4, this.licence_number);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, email);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            // Close resources
            preparedStatement.close();
            connection.close();

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
    }
}
