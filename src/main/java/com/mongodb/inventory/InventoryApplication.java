/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

}
