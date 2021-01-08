package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.model.Order;

public interface IOrderService {

	Order getSummary(String token);

	Long placeOrder(String token);

}
