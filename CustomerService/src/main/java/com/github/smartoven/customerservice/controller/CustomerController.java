package com.github.smartoven.customerservice.controller;

import com.github.smartoven.customerservice.model.customer.Customer;
import com.github.smartoven.customerservice.model.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(@Autowired CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    @PostMapping("/")
    public void save(@RequestBody Customer customer) {
        repository.save(customer);
    }

    @PutMapping("/")
    public void update(@RequestBody Customer customer) {
        repository.save(customer);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody Customer customer) {
        repository.delete(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
