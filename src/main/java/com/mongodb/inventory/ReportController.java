
/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory;

import com.mongodb.inventory.beans.RequestInventoryHistory;
import com.mongodb.inventory.beans.RequestPurchaseHistory;
import com.mongodb.inventory.exception.ApplicationException;
import com.mongodb.inventory.model.Inventory;
import com.mongodb.inventory.model.Purchase;
import com.mongodb.inventory.repository.InventoryRepository;
import com.mongodb.inventory.repository.PurchaseRepository;
import com.mongodb.inventory.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    private EntityManager entityManager;

    //TODO
    // create methods for:
    // - retrieving the history of a purchase order
    // - retrieving the history of the inventory on a given time interval


    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> report(@RequestBody RequestInventoryHistory request) throws ApplicationException {
        List<Inventory> inventories = new ArrayList<>();
        inventories = inventoryRepository.report(request.getProduct_id(), request.getFrom(), request.getTo());

        logger.info("date from [{}] to [{}]", request.getFrom(), request.getTo());

        if ( (inventories!=null) || (inventories.size()>0) ) {
            return ResponseEntity.ok().body(inventories);
        } else {
            new ApplicationException("It was not possible to retrieve the history of the product " + request.getProduct_id());
        }
        return null;
    }



    @GetMapping("/order/{id}")
    public ResponseEntity<List<Purchase>> getPurchaseHistory(@RequestBody RequestPurchaseHistory request) throws ApplicationException {
        List<Purchase> purchases = new ArrayList<>();
//        purchases = purchaseRepository.purchaseHistoryById(request.getOrder_id(), request.getFrom(), request.getTo());

        purchases = (List<Purchase>) entityManager.createNativeQuery("SELECT * from purchase for system_time between :dateFrom and :dateTo where id = :id", Purchase.class)
                .setParameter("id", request.getOrder_id())
                .setParameter("dateFrom", request.getFrom())
                .setParameter("dateTo", request.getTo())
                .getResultList();

        if ( (purchases!=null) || (purchases.size()>0) ) {
            return ResponseEntity.ok().body(purchases);
        } else {
            new ApplicationException("It was not possible to retrieve the history of the orders " + request.getOrder_id());
        }
        return null;
    }
}
