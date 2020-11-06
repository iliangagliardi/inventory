package com.mongodb.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mongodb.inventory.exception.ApplicationException;
import com.mongodb.inventory.model.Inventory;
import com.mongodb.inventory.model.Purchase;
import com.mongodb.inventory.model.Product;
import com.mongodb.inventory.model.PurchaseItem;
import com.mongodb.inventory.repository.InventoryRepository;
import com.mongodb.inventory.repository.PurchaseRepository;
import com.mongodb.inventory.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/orders")
public class PurchaseController {

    Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    InventoryRepository inventoryRepository;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
        try {
            List<Product> products = new ArrayList<Product>();
            products = productRepository.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity getOrder(@PathVariable("id") long id) {
        logger.debug("getOrder({})", id);
        try {
            Optional<Purchase> order = purchaseRepository.findById(id);
            logger.debug("order={}", order);

            if (order.isPresent()) {
                return new ResponseEntity(order.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("err", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackOn = ApplicationException.class)
    @PostMapping("/place")
    public Purchase placeOrder(@Valid @RequestBody Purchase purchaseRequest) throws ApplicationException {
        Purchase savedPurchase = null;
        Purchase init = new Purchase();
        // manipulate the object to have the official insertion date of the order
        if (purchaseRequest.getDate() == null) {
            init.setDate(new Date());
        } else {
            init.setDate(purchaseRequest.getDate());
        }
        if (purchaseRequest.getCustomer() != null) {
            init.setCustomer(purchaseRequest.getCustomer());
        }
        // save the purchase
        savedPurchase = purchaseRepository.save(init);
        for (PurchaseItem element : purchaseRequest.getItems()) {
            element.setPurchase(new Purchase(init.getId()));
            savedPurchase.addPurchaseItem(element);
        }

        // update inventory
        for (PurchaseItem item : savedPurchase.getItems()) {
            Inventory i = (Inventory) inventoryRepository.findByProductId(item.getProduct().getId());
            logger.info("inventory {} stock {} -quantity {}", i.getId(), i.getStock(), item.getQuantity());
            if (i.getStock() < item.getQuantity())
                throw new ApplicationException("Not enough quantity in stock for product " + item.getProduct().getName());
            i.setStock(i.getStock() - item.getQuantity());
            inventoryRepository.save(i);
        }

        return savedPurchase;
    }


    @Transactional
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPurchase(@PathVariable(value = "id") Long purchaseId) throws ApplicationException {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ApplicationException("Purchase order " + purchaseId + " not found"));

        purchase.setCanceled(true);
        purchaseRepository.save(purchase);
        return ResponseEntity.ok("saved");
    }

    @PutMapping("/shipped/{id}")
    public ResponseEntity<String> shippedPurchase(@PathVariable(value = "id") Long purchaseId) throws ApplicationException {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ApplicationException("Purchase order " + purchaseId + " not found"));
        purchase.setShipped(true);
        purchaseRepository.save(purchase);
        return ResponseEntity.ok("saved");
    }

    @PutMapping("/delivered/{id}")
    public ResponseEntity<String> deliveredPurchase(@PathVariable(value = "id") Long purchaseId) throws ApplicationException {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ApplicationException("Purchase order " + purchaseId + " not found"));
        purchase.setDelivered(true);
        purchaseRepository.save(purchase);
        return ResponseEntity.ok("saved");
    }

}
