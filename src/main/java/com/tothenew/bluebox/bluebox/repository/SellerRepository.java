package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SellerRepository extends PagingAndSortingRepository<Seller, Long> {

  Seller findByEmailIgnoreCase(String email);

  @Query(value = "SELECT id, firstName, middleName,lastName, email, isActive, companyName, companyContact from Seller s  ")
  List<Object> findAllSellers(Pageable pageable);
}
