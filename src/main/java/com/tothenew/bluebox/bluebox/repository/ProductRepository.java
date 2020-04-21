package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
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

//  @Query(value = "select a.name AS ProductName,a.description,a.brand,a.is_cancellable,a.is_returnable,a.is_active,b.name AS CategoryName " +
//      "FROM product a inner join category b " +
//      "ON a.category_id = b.id " +
//      "Where a.seller_user_id=:sellerId",nativeQuery = true)

  @Query(value = "select * from product Where seller_user_id=:sellerId AND is_deleted = false", nativeQuery = true)
  List<Product> listAllProduct(Pageable paging, Long sellerId);

  @Query(value = "select category_id from product where id=:productId and is_deleted=0 and is_active=1 ", nativeQuery = true)
  Optional<Long> findCategoryidIsActive(Long productId);

  @Query(value = "select * from product where product_name LIKE %:query% and is_deleted=0 and is_active=1 and category_id=:categoryId", nativeQuery = true)
  List<Map<Object, Object>> findAllProductsForCustomer(Long categoryId, Pageable pageable,
      String query);

  @Query(value = "select * from product where is_deleted=0 and is_active=1 and category_id=:categoryId", nativeQuery = true)
  List<Map<Object, Object>> findAllProductsForCustomer(Long categoryId, Pageable pageable);

  @Query(value =
      "select a.name AS ProductName,a.description,a.brand,a.is_cancellable,a.is_returnable,a.is_active As ProductActive,"
          +
          "b.quantity_available,b.price,b.primary_image_name,b.is_active As ProductVariationActive,"
          +
          "c.name As CategoryName from " +
          "product_variation b INNER JOIN product a " +
          "ON b.product_id=a.id " +
          "INNER JOIN category c " +
          "ON a.category_id=c.id " +
          "WHERE c.is_leaf_node=true AND c.id=:id", nativeQuery = true)
  List<Map<Object, Object>> listAllProductCustomer(Pageable paging, Long id);
}

