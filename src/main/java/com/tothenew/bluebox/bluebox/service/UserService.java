package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  ProductRepository productRepository;

  /*
  Returns a List of all Product sorted by Id
   */
  public List<Product> returnProductList() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    return productRepository.findAll(pageable).toList();
  }

}
