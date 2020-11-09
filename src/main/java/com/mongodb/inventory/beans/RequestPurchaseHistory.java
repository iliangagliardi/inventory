/*
 *
 * Author <ilian.gagliardi@mongodb.com>
 * Copyright (c) MongoDB 2020.
 */

package com.mongodb.inventory.beans;

import java.util.Date;
import java.util.Objects;

public class RequestPurchaseHistory {

    private Date from;

    private Date to;

    private Long order_id;

    public RequestPurchaseHistory() {
    }

    public RequestPurchaseHistory(Date from, Date to, Long order_id) {
        this.from = from;
        this.to = to;
        this.order_id = order_id;
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

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }


    @Override
    public String toString() {
        return "RequestPurchaseHistory{" +
                "from=" + from +
                ", to=" + to +
                ", order_id=" + order_id +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPurchaseHistory that = (RequestPurchaseHistory) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(order_id, that.order_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, order_id);
    }
}

