package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  User findByEmailIgnoreCase(String email);
}
