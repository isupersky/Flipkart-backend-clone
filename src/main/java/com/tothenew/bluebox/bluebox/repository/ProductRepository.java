package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
