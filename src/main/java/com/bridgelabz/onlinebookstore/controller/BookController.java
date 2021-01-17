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
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/book")
public class BookController {

	@Autowired
	public IBookStoreService bookStoreService;

	@ApiOperation("For getting all books")
	@GetMapping("/getBooks")
	public ResponseEntity<Response> getAllBooks() throws BookException {
		List<Book> booksList = bookStoreService.getAllBooks();
		if(booksList != null)
			return new ResponseEntity<>(new Response(200, "Returned all books successfully", booksList), HttpStatus.OK);
		return new ResponseEntity<>(new Response(400, "Don't have any books!!"), HttpStatus.NOT_ACCEPTABLE);
	}

	@ApiOperation("To get book by book id")
	@GetMapping("/getBook/{bookId}")
	public ResponseEntity<Response> getBookDataByBookId(@PathVariable("bookId") long bookId) throws BookException {
		Book booksList = bookStoreService.getBookDataByBookId(bookId);
		if(booksList != null)
			return new ResponseEntity<>(new Response(200, "Get call for ID successfull", booksList), HttpStatus.OK);
		return new ResponseEntity<>(new Response(400, "Book does not exists!!"), HttpStatus.NOT_ACCEPTABLE);
	}

	@ApiOperation("For sorting the books by price from high to low")
	@GetMapping("/sort/price/descending")
	public ResponseEntity<Response> sortBooksByPriceFromLowToHigh() {
		List<Book> booksList = bookStoreService.sortBooksByPriceFromLowToHigh();
		if(booksList != null)
			return new ResponseEntity<>(new Response(200, "Books returned in ascending order by price", booksList), HttpStatus.OK);
		return new ResponseEntity<>(new Response(400, "Books do not exist!!"), HttpStatus.NOT_ACCEPTABLE);

	}

	@ApiOperation("For sorting the books by price from low to high")
	@GetMapping("/sort/price/ascending")
	public ResponseEntity<Response> sortBooksByPriceFromHighToLow() {
		List<Book> booksList = bookStoreService.sortBooksByPriceFromHighToLow();
		if(booksList != null)
			return new ResponseEntity<>(new Response(200, "Books returned in descending order by price", booksList), HttpStatus.OK);
		return new ResponseEntity<>(new Response(400, "Books do not exist!!"), HttpStatus.NOT_ACCEPTABLE);
	}

	@ApiOperation("For Inserting a book")
	@PostMapping("/create")
	public ResponseEntity<Response> createBookData(@RequestBody BookDTO bookDTO) {
		Book booksList = bookStoreService.createBookData(bookDTO);
		return new ResponseEntity<>(new Response(200, "Inserted book data successfully!!", booksList), HttpStatus.OK);
	}

	@ApiOperation("For updating a book details by book id")
	@PutMapping("/update/{bookId}")
	public ResponseEntity<Response> updateBookDataByBookId(@PathVariable("bookId") long bookId, @Valid @RequestBody BookDTO bookDTO) {
		Book booksList = bookStoreService.updateBookDataByBookId(bookId, bookDTO);
		return new ResponseEntity<>(new Response(200, "Updated book data successfully!!", booksList), HttpStatus.OK);
	}

	@ApiOperation("For deleting a book details by book id")
	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Response> deleteBookDataByBookId(@PathVariable("bookId") long bookId) {
		bookStoreService.deleteBookDataByBookId(bookId);
		return new ResponseEntity<>(new Response(200, "Deleted successfully!!", "Deleted id:" + bookId), HttpStatus.OK);
	}

	@ApiOperation("For counting number of records in database")
	@GetMapping("/count")
	public ResponseEntity<Response> list() {
		long count = bookStoreService.count();
		return new ResponseEntity<>(new Response(200, "Got count of books successfully!!", count), HttpStatus.OK);
	}

	@ApiOperation("For sorting the books by new arrivals")
	@GetMapping("/sort/newArrivals")
	public ResponseEntity<Response> sortBooksByNewArrivals() {
		List<Book> booksList = bookStoreService.sortBooksByNewArrivals();
		if(booksList != null)
			return new ResponseEntity<>(new Response(200, "Books returned in descending order by price", booksList),HttpStatus.OK);
		return new ResponseEntity<>(new Response(400, "Books do not exist!!"), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ApiOperation("To get book by book name")
	@GetMapping("/getBookList/{bookName}")
	public ResponseEntity<Response> getBookDataByBookName(@PathVariable("bookName") String bookName) {
		List<Book> books = bookStoreService.getBooksByBookName(bookName);
		if(books != null)
			return new ResponseEntity<>(new Response(200, "Get call for book successfull", books), HttpStatus.OK);
		else 
			return new ResponseEntity<>(new Response(400, "Book does not exists!!"), HttpStatus.NOT_ACCEPTABLE);
	}
	
}
