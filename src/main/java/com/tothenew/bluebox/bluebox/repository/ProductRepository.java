package com.tothenew.bluebox.bluebox.repository;

import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import java.util.List;
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

}

