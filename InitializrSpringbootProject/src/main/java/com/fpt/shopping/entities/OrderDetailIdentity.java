package com.fpt.shopping.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class OrderDetailIdentity implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @NotNull
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public OrderDetailIdentity(@NotNull Order order, @NotNull Product product) {
        this.order=order;
        this.product=product;
    }

    public OrderDetailIdentity() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order=order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product=product;
    }
}
