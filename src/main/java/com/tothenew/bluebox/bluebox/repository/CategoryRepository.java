package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Category;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

  Category findByNameIgnoreCase(String name);

  @Query(value = "select * from category where name=:name", nativeQuery = true)
  List<Map<Object, Object>> findCategory(String name);

  @Query(value = "select * from category where is_leaf_node = true", nativeQuery = true)
  List<Category> findAllCategory(Pageable pageable);

  @Query(value = "select id, name, is_leaf_node from category where parent_id=:id", nativeQuery = true)
  List<Object> findAllChildren(Long id);

  @Query(value = "select * from category where name=:name and parent_id=:id", nativeQuery = true)
  Category findByNameAndParent(String name, Long id);


  @Query(value =
      "select id, name from category where parent_id=:value", nativeQuery = true)
  List<Map<Object, Object>> findByParentCategory(Long value);


  @Query(value = "select id,name " +
      "from category " +
      "where parent_id is null", nativeQuery = true)
  List<Map<Object, Object>> findByCategoryIsNull();

}
