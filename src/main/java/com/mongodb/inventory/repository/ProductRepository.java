package com.mongodb.inventory.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mongodb.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>  {

    List<Product> findByName(String name);

}
