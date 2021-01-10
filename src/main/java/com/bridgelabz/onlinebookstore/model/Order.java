package com.bridgelabz.onlinebookstore.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Table(name = "ordered_items")
public @Data class Order {

	@Id
	private long orderId;

	private long userId;
	private double totalPrice;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@OneToMany()
	public List<Cart> cartItems;

	public Order(Long orderId, Long userId, List<Cart> cartItems, double totalPrice) {
		this.orderId = orderId;
		this.userId = userId;
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
	}

}