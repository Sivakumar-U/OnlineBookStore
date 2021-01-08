package com.bridgelabz.onlinebookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.service.ICartService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@ApiOperation(value = "For getting all books in the cart")
	@GetMapping("/get")
	public ResponseEntity<Response> showCart(@RequestHeader String token) {
		List<Cart> userCart = cartService.listCartItems(token);
		return new ResponseEntity<>(new Response(200, "Got all books from cart successfully", userCart), HttpStatus.OK);
	}

	@ApiOperation(value = "For getting count of all books in the cart")
	@GetMapping("/getCount")
	public int showCartCount(@RequestHeader String token) {
		List<Cart> userCart = cartService.listCartItems(token);
		return userCart.size();
	}

	@ApiOperation(value = "For adding the book to cart")
	@PostMapping("/add/{bookId}")
	public ResponseEntity<Response> addToCart(@PathVariable("bookId") Long bookId, @RequestHeader String token) throws BookException {
		Cart cartItem = cartService.addBookToCart(token, bookId, 1);
		return new ResponseEntity<>(new Response(200, "Book added to cart successfully", cartItem), HttpStatus.OK);

	}

	@ApiOperation(value = "For updating book quantity")
	@PostMapping("/update/{bookId}/{orderQuantity}")
	public String updateBookOrderQuantity(@PathVariable("bookId") Long bookId,
			@PathVariable("orderQuantity") Integer orderQuantity, @RequestHeader String token) throws BookException {
		double subtotal = cartService.updateOrderQuantity(bookId, orderQuantity, token);
		return String.valueOf(subtotal);
	}

	@ApiOperation(value = "For removing book from Cart")
	@DeleteMapping("/remove/{bookId}/{userId}")
	public ResponseEntity<Response> removeFromCart(@PathVariable("bookId") Long bookId, @RequestHeader String token) throws BookException {
		cartService.removeProduct(bookId, token);
		return new ResponseEntity<>(new Response(200, "Book removed from cart successfully"), HttpStatus.OK);
	}

	@ApiOperation(value = "For getting all books from wishlist")
	@GetMapping("/wishlist/all")
	public List<Cart> getWishListBooks(@RequestHeader("token") String token) {
		return cartService.getAllBooksFromWishList(token);
	}

	@ApiOperation(value = "For adding book to wishlist")
	@PostMapping("/wishlist/add/{bookId}")
	public Response addToWishList(@PathVariable("bookId") Long bookId, @RequestHeader("token") String token) throws BookException {
		return cartService.addBookToWishList(bookId, token);
	}

	@ApiOperation(value = "For deleting book from wishlist")
	@DeleteMapping("/wishlist/delete/{bookId}")
	public ResponseEntity<Response> deleteBookFromWishlist(@PathVariable("bookId") Long bookId, @RequestHeader("token") String token) {
		List<Cart> cart = cartService.deleteBookFromWishlist(bookId, token);
		return new ResponseEntity<>(new Response(200, "Book removed from wishlist", cart), HttpStatus.OK);
	}

	@ApiOperation(value = "For putting books wishlist to cart")
	@PutMapping("/wishlist/addTocart/{bookId}")
	public Response addBookFromWishlistToCart(@PathVariable("bookId") Long bookId, @RequestHeader("token") String token) {
		return cartService.addBookFromWishlistToCart(bookId, token);
	}

}
