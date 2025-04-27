package com.chanllenge.batch.infrastructure.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanllenge.batch.domain.entity.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
