package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValues;
import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValuesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMetadataFieldValuesRespository extends
    JpaRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> {

}
