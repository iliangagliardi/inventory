/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory;

import com.mongodb.inventory.model.Inventory;
import com.mongodb.inventory.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    InventoryRepository inventoryRepository;

    //TODO
    // add a method to update quantities for a product in inventory

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getInventory(@RequestParam(required = false) String name) {
        try {
            List<Inventory> inventory = new ArrayList<Inventory>();
            inventory = inventoryRepository.findAll();
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("err",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/setup")
    public ResponseEntity<List<Inventory>> setup(@RequestBody List<Inventory> list) {
        try {
            if (list != null) {
                logger.info("received a list {}", list.size());
            } else {

            }
            List<Inventory> retList = inventoryRepository.saveAll(list);
            return new ResponseEntity<>(retList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
