/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.model;

import java.io.Serializable;

public class PurchaseCompositeKey implements Serializable {

    private Long id;

    private String uid;


    public PurchaseCompositeKey() {
    }

    public PurchaseCompositeKey(Long id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
