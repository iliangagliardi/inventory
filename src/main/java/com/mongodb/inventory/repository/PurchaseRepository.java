/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.repository;

import com.mongodb.inventory.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>  {



    @Query(value = "select id,canceled,customer,date,delivered,shipped,start_time,end_time from Purchase for system_time between ?2 and ?3 where id = ?1", nativeQuery = true)
    List<Purchase> purchaseHistoryById(Long order_id, Date from, Date to);

}
