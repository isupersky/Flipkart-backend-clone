package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValues;
import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValuesId;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldValuesRespository extends
    JpaRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> {


  @Query(value = "select "
      + "c.category_metadata_field_id, "
      + "c.value, "
      + "d.name "
      + "from "
      + "category_metadata_field_values c "
      + "join category_metadata_field d ON c.category_metadata_field_id = d.id "
      + "where "
      + "c.category_id=:id", nativeQuery = true)
  List<Map<Object, Object>> findByCategoryId(Long id);
}
