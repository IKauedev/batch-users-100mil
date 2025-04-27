package com.chanllenge.batch.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanllenge.batch.entity.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
