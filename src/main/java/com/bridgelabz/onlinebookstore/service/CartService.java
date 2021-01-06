package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.BookStoreRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CartService implements ICartService {

	@Autowired
	private JwtGenerator generator;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BookStoreRepository bookStoreRepository;

	@Autowired
	public UserRepository userRepository;

	@Override
	public Cart addBookToCart(String token, Long bookId, Integer order_quantity) {
		Long userId = generator.decodeJWT(token);
		Book book = bookStoreRepository.findById(bookId).get();
		User user = userRepository.findById(userId).orElse(null);

		Cart cartItem = cartRepository.findByUserIdAndBookId(userId, bookId);

		if (cartItem != null) {
			cartItem.setOrderQuantity(order_quantity);
		} else {
			cartItem = new Cart();
			cartItem.setOrderQuantity(order_quantity);
			cartItem.setUser(user);
			cartItem.setBook(book);
			bookStoreRepository.updateQuantityAfterOrder(book.getQuantity()-order_quantity,bookId);
		}
		cartRepository.save(cartItem);
		return null;
	}

	@Override
	public double updateOrderQuantity(Long bookId, Integer order_quantity, String token) {
		Long userId = generator.decodeJWT(token);
		Book book = bookStoreRepository.findById(bookId).get();

		double subtotal =0;
		if(book.getQuantity() >= order_quantity) {
			cartRepository.updateOrderQuantity(order_quantity, bookId, userId);
			subtotal = book.getPrice() * order_quantity;
			bookStoreRepository.updateQuantityAfterOrder(book.getQuantity()-order_quantity,bookId);
			return subtotal;
		}
		else {
			return subtotal; 
		}
	}

	@Override
	public List<Cart> listCartItems(String token) {
		Long userId = generator.decodeJWT(token);
		return cartRepository.findByUserId(userId);
	}

	@Override
	public void removeProduct(Long bookId, String token) {
		Long userId = generator.decodeJWT(token);
		cartRepository.deleteByUserAndBook(userId, bookId);
	}

}
