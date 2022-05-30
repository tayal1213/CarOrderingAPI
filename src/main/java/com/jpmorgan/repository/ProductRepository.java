package com.jpmorgan.repository;


import com.jpmorgan.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDetails, Long> {

    @Query(value =  "SELECT new ProductDetails(pd.productId, pd.productName, pd.stockAvailble, pd.productInfo, pd.productStatus) from ProductDetails  pd where pd.productStatus='ACTIVE'")
    List<ProductDetails> findAllAvailbleProducts();

    @Query(value =  "SELECT pd.productId, pd.orders from ProductDetails  pd where pd.productId= :id")
    List<ProductDetails> findAllProductswithOrders(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value =  "Update ProductDetails pd set pd.productStatus = ?2 WHERE pd.productId =?1")
    void updateProductStatus(Long id, String status);

    @Query(value =  "SELECT new ProductDetails(pd.productId, pd.productName, pd.stockAvailble, pd.productInfo, pd.productStatus) from ProductDetails  pd where pd.productName=?1 ")
    ProductDetails findByProductName(String productName);
}
