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

  @Query(value = "select pv.id, "
      + "pv.is_active,pv.metadata,pv.price,pv.primary_image_name,pv.quantity_available,pv.secondary_image_name "
      + " from product_variation pv"
      + " Join"
      + " product p"
      + " on p.id = pv.product_id "
      + "where p.id=:productId", nativeQuery = true)
  List<Object> findVariationsByProductId(Long productId, Pageable pageable);
}
