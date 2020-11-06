package com.mongodb.inventory.beans;

import java.io.Serializable;
import java.util.Date;

public class RequestInventoryHistory {

    private long product_id;

    private Date from;

    private Date to;

    public RequestInventoryHistory() {
    }

    public RequestInventoryHistory(long product_id, Date from, Date to) {
        this.product_id = product_id;
        this.from = from;
        this.to = to;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "RequestInventoryHistory{" +
                "product_id=" + product_id +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
