/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.repository;

import com.mongodb.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {



    Inventory findByProductId(Long id);

    // SELECT * from inventory for system_time between '11/06/2020 14:30:00' and '11/06/2020 14:50:00' where product_id = 2
    // if using the regular ID field on the table, JPA was not able to have distinct rows. This workaround render the results perfectly but loses the real ID
    @Query(value = "select row_number() over (order by end_time desc) as id, stock, product_id, start_time, end_time from Inventory for system_time between ?2 and ?3 where product_id = ?1", nativeQuery = true)
    List<Inventory> report(Long product_id, Date from, Date to);






}
