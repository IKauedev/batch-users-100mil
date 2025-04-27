package com.chanllenge.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.chanllenge.batch.domain.entity.customer.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
	@Override
	public Customer process(Customer customer) throws Exception {
		customer.setFullName(String.join(" ", customer.getFirstName(), customer.getLastName()));
		return customer;
	}
}
