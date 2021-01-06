package com.bridgelabz.onlinebookstore.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ordered_items")
public @Data class Order {
	
	@Id
	private long orderId;
	
	private long userId;
	private double totalPrice;
	private LocalDate orderDate;
	
	@OneToMany
	public List<Cart> cartItems;
	
	public Order() {}
	public Order(Long orderId, Long userId, List<Cart> cartItems, double totalPrice) {
        this.orderId=orderId;
        this.userId=userId;
        this.cartItems=cartItems;
        this.totalPrice=totalPrice;
        this.orderDate=LocalDate.now();
    }

}
