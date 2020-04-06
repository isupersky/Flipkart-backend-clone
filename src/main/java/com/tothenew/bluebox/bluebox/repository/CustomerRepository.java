package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  User findByEmailIgnoreCase(String email);

  @Query(value = "Select id, firstName, middleName,lastName, email, isActive from Customer")
  List<Object> findAllCustomers(Pageable pageable);
}
