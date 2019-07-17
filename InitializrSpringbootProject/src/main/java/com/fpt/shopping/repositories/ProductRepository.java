package com.fpt.shopping.repositories;

import com.fpt.shopping.entities.Category;
import com.fpt.shopping.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsByCategory(Category category);
}
