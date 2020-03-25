package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.enitity.User;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  User findByEmailIgnoreCase(String email);
}
