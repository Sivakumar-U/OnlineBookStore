package com.bridgelabz.onlinebookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.service.IBookStoreService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	public IBookStoreService bookStoreService;

	@ApiOperation("For getting all books")
	@GetMapping("/getBooks")
	public ResponseEntity<Response> getAllBooks() throws BookException {
		List<Book> booksList = bookStoreService.getAllBooks();
		return new ResponseEntity<>(new Response(200, "Returned all books successfully", booksList), HttpStatus.OK);
	}

	@ApiOperation("To get book by book id")
	@GetMapping("/getBook/{bookId}")
	public ResponseEntity<Response> getBookDataByBookId(@PathVariable("bookId") long bookId) throws BookException {
		Book booksList = bookStoreService.getBookDataByBookId(bookId);
		return new ResponseEntity<>(new Response(200, "Get call for ID successfull", booksList), HttpStatus.OK);

	}

	@ApiOperation("For sorting the books by price from high to low")
	@GetMapping("/sort/price/descending")
	public ResponseEntity<Response> sortBooksByPriceFromLowToHigh() throws BookException {
		List<Book> booksList = bookStoreService.sortBooksByPriceFromLowToHigh();
		return new ResponseEntity<>(new Response(200, "Books returned in ascending order by price", booksList),
				HttpStatus.OK);
	}

	@ApiOperation("For sorting the books by price from low to high")
	@GetMapping("/sort/price/ascending")
	public ResponseEntity<Response> sortBooksByPriceFromHighToLow() throws BookException {
		List<Book> booksList = bookStoreService.sortBooksByPriceFromHighToLow();
		return new ResponseEntity<>(new Response(200, "Books returned in descending order by price", booksList),
				HttpStatus.OK);
	}

	@ApiOperation("For Inserting a book")
	@PostMapping("/create")
	public ResponseEntity<Response> createBookData(@RequestBody BookDTO bookDTO) throws BookException {
		Book booksList = bookStoreService.createBookData(bookDTO);
		return new ResponseEntity<>(new Response(200, "Inserted book data successfully!!", booksList), HttpStatus.OK);
	}

	@ApiOperation("For updating a book details by book id")
	@PutMapping("/update/{bookId}")
	public ResponseEntity<Response> updateBookDataByBookId(@PathVariable("bookId") long bookId,
			@Valid @RequestBody BookDTO bookDTO) throws BookException {
		Book booksList = bookStoreService.updateBookDataByBookId(bookId, bookDTO);
		return new ResponseEntity<>(new Response(200, "Updated book data successfully!!", booksList), HttpStatus.OK);
	}

	@ApiOperation("For deleting a book details by book id")
	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Response> deleteBookDataByBookId(@PathVariable("bookId") long bookId) throws BookException {
		bookStoreService.deleteBookDataByBookId(bookId);
		return new ResponseEntity<>(new Response(200, "Deleted successfully!!", "Deleted id:" + bookId), HttpStatus.OK);
	}
	
	@ApiOperation("For counting number of records in database")
	@GetMapping("/count")
	public long list() {
		return bookStoreService.count();
	}
	
	@ApiOperation("For sorting the books by new arrivals")
	@GetMapping("/sort/newArrivals")
	public ResponseEntity<Response> sortBooksByNewArrivals() throws BookException {
		List<Book> booksList = bookStoreService.sortBooksByNewArrivals();
		return new ResponseEntity<>(new Response(200, "Books returned in descending order by price", booksList),HttpStatus.OK);
	}
}
