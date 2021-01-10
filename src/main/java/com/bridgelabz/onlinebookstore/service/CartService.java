package com.bridgelabz.onlinebookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.BookStoreRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;

@Service
@Transactional
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BookStoreRepository bookStoreRepository;

	@Autowired
	public UserRepository userRepository;

	@Override
	public Cart addBookToCart(String token, Long bookId, Integer order_quantity) {
		Long userId = JwtGenerator.decodeJWT(token);
		Book book = bookStoreRepository.findById(bookId).orElse(null);
		if (book == null)
			return null;
		else {
			User user = userRepository.findById(userId).orElse(null);

			Cart cartItem = cartRepository.findByUserIdAndBookId(userId, bookId);

			if (cartItem != null) {
				cartItem.setOrderQuantity(order_quantity);
			} else {
				cartItem = new Cart();
				cartItem.setOrderQuantity(order_quantity);
				cartItem.setUser(user);
				cartItem.setBook(book);
				cartItem.setOrdered(false);
				bookStoreRepository.updateQuantityAfterOrder(book.getQuantity() - order_quantity, bookId);

			}
			cartRepository.save(cartItem);
			return cartItem;
		}
	}

	@Override
	public String updateOrderQuantity(Long bookId, Integer order_quantity, String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Book book = bookStoreRepository.findById(bookId).orElse(null);
		if (book == null)
			return null;
		else {
			double subtotal = 0;
			if (book.getQuantity() >= order_quantity) {
				cartRepository.updateOrderQuantity(order_quantity, bookId, userId);
				subtotal = book.getPrice() * order_quantity;
				bookStoreRepository.updateQuantityAfterOrder(book.getQuantity() - order_quantity, bookId);
				return String.valueOf(subtotal);
			} else {
				return "Last "+book.getQuantity()+" are left.";
			}
		}
	}

	@Override
	public List<Cart> listCartItems(String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		return cartRepository.findByUserId(userId);
	}

	@Override
	public void removeProduct(Long bookId, String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Book book = bookStoreRepository.findById(bookId).orElse(null);
		if (book == null)
			return ;
		cartRepository.deleteByUserAndBook(userId, bookId);
	}

	@Override
	public List<Cart> getAllBooksFromWishList(String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		List<Cart> cartItems = cartRepository.findByUserId(userId).stream().filter(Cart::isInWishList)
				.collect(Collectors.toList());
		if (cartItems.isEmpty())
			return new ArrayList<>();
		return cartItems;
	}

	@Override
	public Response addBookToWishList(Long bookId, String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Cart cartItem = cartRepository.findByUserIdAndBookId(userId, bookId);
		Long cartBookId = cartRepository.findDuplicateBookId(bookId);
		if (cartBookId != bookId) {
			if (cartItem != null && cartItem.isInWishList()) {
				return new Response(200, "Book already present in wishlist");
			} else if (cartItem != null && !cartItem.isInWishList()) {
				return new Response(200, "Book already added to Cart");
			} else {
				Book book = bookStoreRepository.findById(bookId).orElse(null);
				if (book == null)
					return null;
				Cart cart = new Cart();
				User user = userRepository.findById(userId).orElse(null);
				cart.setUser(user);
				cart.setBook(book);
				cart.setOrderQuantity(1);
				cart.setInWishList(true);
				cartRepository.save(cart);
				return new Response(200, "Book added to WishList");
			}
		}
		return new Response(200, "Book already present in wishlist");
	}

	@Override
	public List<Cart> deleteBookFromWishlist(Long bookId, String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		List<Cart> cartItems = cartRepository.findByUserId(userId).stream().filter(Cart::isInWishList)
											.collect(Collectors.toList());
		List<Cart> selectedItems = cartItems.stream().filter(cartItem -> cartItem.getBook().getBookId().equals(bookId))
											.collect(Collectors.toList());
		for (Cart book : selectedItems) {
			cartRepository.delete(book);
		}
		return cartRepository.findByUserId(userId);
	}

	@Override
	public Response addBookFromWishlistToCart(Long bookId, String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Cart cartItem = cartRepository.findByUserIdAndBookId(userId, bookId);
		if (cartItem.isInWishList()) {
			cartItem.setInWishList(false);
			cartRepository.save(cartItem);
			return new Response(HttpStatus.OK.value(), "Successfully added book fromwishlist to cart.");
		}
		return new Response(200, "Already present in cart, ready to checkout");
	}

}
