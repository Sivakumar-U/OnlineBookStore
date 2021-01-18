package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.repository.BookStoreRepository;

@Service
public class BookStoreService implements IBookStoreService {

	@Autowired
	public BookStoreRepository bookstoreRepository;

	@Override
	public List<Book> getAllBooks() {
		List<Book> booksList = bookstoreRepository.findAll();
		if (booksList.isEmpty()) {
			return null;
		}
		return booksList;
	}

	public Book getBookDataByBookId(long bookId) {
		return bookstoreRepository.findById(bookId).orElse(null);
	}

	@Override
	public List<Book> sortBooksByPriceFromLowToHigh() {
		List<Book> booksList = getAllBooks();
		if (booksList.isEmpty()) {
			return null;
		}
		return booksList.stream().sorted((firstBook, secondBook) -> (int) (secondBook.price - firstBook.price))
				.collect(Collectors.toList());
	}

	@Override
	public List<Book> sortBooksByPriceFromHighToLow() {
		List<Book> booksList = getAllBooks();
		if (booksList.isEmpty()) {
			return null;
		}
		return booksList.stream().sorted((firstBook, secondBook) -> (int) (firstBook.price - secondBook.price))
				.collect(Collectors.toList());
	}

	@Override
	public Book createBookData(BookDTO bookDTO) {
		Book bookData = null;
		bookData = new Book(bookDTO);
		return bookstoreRepository.save(bookData);
	}

	@Override
	public Book updateBookDataByBookId(long bookId, BookDTO bookDTO) {
		Book bookData = this.getBookDataByBookId(bookId);
		bookData.updateBookDataByBookId(bookDTO);
		return bookstoreRepository.save(bookData);
	}

	@Override
	public void deleteBookDataByBookId(long bookId) {
		Book bookData = this.getBookDataByBookId(bookId);
		bookstoreRepository.delete(bookData);
	}

	public long count() {
		return bookstoreRepository.count();
	}

	@Override
	public List<Book> sortBooksByNewArrivals() {
		List<Book> booksList = bookstoreRepository.findBookOrderByCreatedDateAndTimeDesc();
		if (booksList.isEmpty()) {
			return null;
		}
		return booksList;
	}

	@Override
	public List<Book> getBooksByBookName(String bookName) {
		List<Book> booksList = bookstoreRepository.findBooksByBookName(bookName);
		if (booksList.isEmpty()) {
			return null;
		}
		return booksList;
	}

}
