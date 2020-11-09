/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.model;

import java.io.Serializable;
import java.util.Date;

public class InventoryCompositeKey implements Serializable {

    private Long id;

    private Date endTime;
}
