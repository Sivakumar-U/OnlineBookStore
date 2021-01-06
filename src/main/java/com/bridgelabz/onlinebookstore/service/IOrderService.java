package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.model.Order;

public interface IOrderService {

	String getSummary(String token);

	Long placeOrder(String token);

}
