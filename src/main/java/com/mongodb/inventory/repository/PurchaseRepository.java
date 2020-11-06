package com.mongodb.inventory.repository;

import com.mongodb.inventory.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>  {


}
