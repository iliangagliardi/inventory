/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//https://hibernate.atlassian.net/browse/HHH-6044


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock")
    private Long stock;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "startTime", insertable = false, updatable = false)
    private Date startTime;

    @Column(name = "endTime", insertable = false, updatable = false)
    private Date endTime;


    // constructor and getters/setters

    public Inventory() {
    }

    public Inventory(Long id, Long stock, Product product, Date startTime, Date endTime) {
        this.id = id;
        this.stock = stock;
        this.product = product;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", stock=" + stock +
                ", product=" + product +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}