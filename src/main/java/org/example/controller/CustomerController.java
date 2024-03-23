package org.example.controller;

import org.example.Customer;
import org.example.dto.CustomerDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
@RestController
public class CustomerController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/users/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customer", customerDto);
        return "registration";
    }

    //TODO in POST umwandeln
   /* @GetMapping("/users/register")
    public Customer register(@RequestParam Map<String,String> requestParams) {
        String id=requestParams.get("id");
        String firstname=requestParams.get("firstname");
        String lastname=requestParams.get("lastname");
        String telnr=requestParams.get("telnr");
        String licensenr=requestParams.get("licensenr");
        String order=requestParams.get("order");
        return new Customer(counter.incrementAndGet(), String.format(id),
                String.format(firstname), String.format(lastname),String.format(telnr), String.format(licensenr), String.format(order));

        //TODO: DB Anbindung
    }*/

    //TODO: Return type zu Session?
    @RequestMapping("/users/login")
    public void login(String user, String pw){

    }

    @RequestMapping("/users/logout")
    public void logout(){

    }

    @RequestMapping("/users/account")
    public void getAccountInfo(){

    }

}
