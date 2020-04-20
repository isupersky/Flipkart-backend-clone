package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {


  List<Object> findByProductId(Pageable paging, Product id);

  List<Object> findByProductId(Product id);

  @Query(value = "select * from product_variation where product_id=:productId", nativeQuery = true)
  List<ProductVariation> findVariationsByProductId(Long productId, Pageable pageable);
}
