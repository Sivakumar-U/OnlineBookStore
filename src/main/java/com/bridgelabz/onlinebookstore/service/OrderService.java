package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	private JwtGenerator generator;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	MailData mailData;

	@Autowired
	private Environment env;

	@Override
	public Order getSummary(String token) {
		Long userId = generator.decodeJWT(token);
		Optional<Order> orders = orderRepository.findByUserId(userId);
		return orders.get();
	}

	@Override
	public Long placeOrder(String token) {
		Long userId = generator.decodeJWT(token);
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
		System.out.println(cart);
		double totalPrice = cart.stream().mapToDouble(book -> book.getSubTotal()).sum();
		System.out.println(totalPrice);
		Order order = new Order(orderId, userId, cart, totalPrice);
		orderRepository.save(order);
		String orderMail = mailData.getOrderMail(orderId, user.getFullName(), totalPrice, cart);
		// sendEmail(user.getEmailId(),"Order Placed Successfully",orderMail);

		final String username = env.getProperty("spring.mail.username");
		final String password = env.getProperty("spring.mail.password");

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailId()));
			message.setSubject("Order confirmation mail");
			message.setText("\nDear " + user.getFullName() + "," + "\n Your order has been successfully placed "
					+ "\nDetails: \n" + orderMail);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return orderId;
	}

	void sendEmail(String to, String subject, String orderSummary) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(orderSummary);
		javaMailSender.send(msg);

	}
}
