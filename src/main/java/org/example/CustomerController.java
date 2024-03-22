package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
@RestController
public class CustomerController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/register")
    public Customer register(@RequestParam Map<String,String> requestParams) {
        String id=requestParams.get("id");
        String firstname=requestParams.get("firstname");
        String lastname=requestParams.get("lastname");
        String telnr=requestParams.get("telnr");
        String licensenr=requestParams.get("licensenr");
        String order=requestParams.get("order");
        return new Customer(counter.incrementAndGet(), String.format(id),
                String.format(firstname), String.format(lastname),String.format(telnr), String.format(licensenr), String.format(order));
    }
}
