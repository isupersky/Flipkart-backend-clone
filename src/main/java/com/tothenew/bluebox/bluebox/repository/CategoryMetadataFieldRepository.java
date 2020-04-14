package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryMetadataFieldRepository extends
    PagingAndSortingRepository<CategoryMetadataField, Long> {

  CategoryMetadataField findByNameIgnoreCase(String name);

  Page<CategoryMetadataField> findAll(Pageable paging);
}
