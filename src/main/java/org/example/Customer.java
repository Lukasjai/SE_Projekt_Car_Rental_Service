package org.example;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vorname;
    private  String nachname;
    private String email;
    private String password;
    public Customer() {
    }

    public Customer(String vorname, String nachname, String email, String password) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfirstName() {
        return vorname;
    }

    public void setfirstName(String vorname) {
        this.vorname = vorname;
    }

    public String getlastName() {
        return nachname;
    }

    public void setlastName(String nachname) {
        this.nachname = nachname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
