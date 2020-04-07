package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  User findByEmailIgnoreCase(String email);

//  boolean existsByEmail(String email);
}
