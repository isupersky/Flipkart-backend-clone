package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.ProductCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  SellerRepository sellerRepository;


  //-----------------------------------------SELLER API-----------------------------------------------
  /*
    Adds new product to the product List
   */
  public ResponseEntity<MessageResponseEntity> addProduct(String email, ProductCO productCO) {

    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Product product = new Product();
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.map(productCO, product);
    product.setSellerUserId(seller);

    List<Product> productList = productRepository.findAllBySellerUserId(seller);

    if (productList != null) {
      productList.forEach(e -> {
        if (e.getBrand().equalsIgnoreCase(productCO.getBrand())
            && e.getCategoryId().getId() == productCO.getCategoryId().getId()) {

        }

      });

    }

    productRepository.save(product);

    return new ResponseEntity<MessageResponseEntity>(
        new MessageResponseEntity(HttpStatus.OK, "Product Added".toUpperCase()),
        HttpStatus.OK);
  }
}
