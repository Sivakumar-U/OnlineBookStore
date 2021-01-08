package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Order;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.OrderRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;
import com.bridgelabz.onlinebookstore.utility.MailData;

@Service
public class OrderService implements IOrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	MailData mailData;

	@Override
	public Order getSummary(String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Optional<Order> orders = orderRepository.findByUserId(userId);
		return orders.get();
	}

	@Override
	public Long placeOrder(String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		User user = userRepository.findById(userId).orElse(null);

		Long orderId = (long) 0;
		boolean isorderIdUnique = false;
		while (!isorderIdUnique) {
			orderId = (long) (100000 + new Random().nextInt(900000));
			Optional<Order> order = orderRepository.findById(orderId);
			if (!order.isPresent()) {
				isorderIdUnique = true;
			}
		}
		List<Cart> cart = cartRepository.findByUserId(userId);
		double totalPrice = cart.stream().mapToDouble(book -> book.getSubTotal()).sum();
		Order order = new Order(orderId, userId, cart, totalPrice);
		orderRepository.save(order);
		String orderMail = mailData.getOrderMail(orderId, user.getFullName(), totalPrice, cart);
		mailData.sendMessage("Order confirmation mail",user.getEmailId(),user.getFullName(),",","\n Your order has been successfully placed.","\nDetails: \n",orderMail);
		return orderId;
	}
}
