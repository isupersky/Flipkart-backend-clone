package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation, Long> {


  List<Object> findByProductId(Pageable paging, Long id);

  List<Object> findByProductId(Long id);
}
