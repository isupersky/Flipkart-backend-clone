package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

  List<Product> findAllBySellerUserId(Seller seller);

  @Query(value = "select brand from product where category_id=:id", nativeQuery = true)
  List<Object> findAllBrandsByCategoryId(Long id);

  @Query(value = "select min(a.price) "
      + "from product_variation a "
      + "join product b "
      + "on a.product_id=b.id "
      + "where b.category_id=:id", nativeQuery = true)
  Object findMinimum(Long id);

  @Query(value = "select max(a.price) "
      + "from product_variation a "
      + "join product b "
      + "on a.product_id=b.id "
      + "where b.category_id=:id", nativeQuery = true)
  Object findMaximum(Long id);


}

