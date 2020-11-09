/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity(name = "Purchase")
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer")
    private String customer;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "shipped", columnDefinition = "bit default 0")
    private boolean shipped;

    @Column(name = "delivered", columnDefinition = "bit default 0")
    private boolean delivered;

    @Column(name = "canceled", columnDefinition = "bit default 0")
    private boolean canceled;

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }


    @JsonManagedReference
    @OneToMany(
            mappedBy = "purchase",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PurchaseItem> items = new ArrayList<PurchaseItem>();


    public void addPurchaseItem(PurchaseItem pi) {
        getItems().add(pi);
    }

    public void removePurchaseItem(PurchaseItem pi) {
        getItems().remove(pi);
    }

    public Purchase() {
    }

    public Purchase(Long id) {
        this.id = id;
    }


    public Purchase(Long id, String customer, Date date, List<PurchaseItem> items) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", date=" + date +
                ", items=" + items +
                '}';
    }
}