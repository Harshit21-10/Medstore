package com.company.MedStore.dao;

import com.company.MedStore.entity.Customer;
import com.company.MedStore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDao {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomerDao(Customer customer){
        if (customerRepository.existsByEmail(customer.getEmail())){
            throw new IllegalArgumentException("Email Already Exists");
        }
        return customerRepository.save(customer);
    }
}
