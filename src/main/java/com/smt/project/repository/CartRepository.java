package com.smt.project.repository;

import com.smt.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c JOIN c.products p WHERE p.category.id = :categoryId")
    List<Cart> findByProductsCategory(@Param("categoryId") UUID categoryId);

}
