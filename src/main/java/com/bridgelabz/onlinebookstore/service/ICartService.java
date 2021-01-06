package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.model.Cart;

public interface ICartService {

	Cart addBookToCart(String token, Long bookId, Integer order_quantity);

	double updateOrderQuantity(Long bookId, Integer orderQuantity, String token);

	List<Cart> listCartItems(String token);

	void removeProduct(Long bookId, String token);

}
