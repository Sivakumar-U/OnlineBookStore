package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.repository.BookStoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class BookStoreService implements IBookStoreService {

	@Autowired
	public BookStoreRepository bookstoreRepository;

	@Override
	public List<Book> getAllBooks() throws BookException {
		List<Book> booksList = bookstoreRepository.findAll();
		if (booksList.isEmpty()) {
			throw new BookException("Books are not available", BookException.ExceptionType.BOOKS_NOT_AVAILABLE);
		}
		return booksList;
	}

	public Book getBookDataByBookId(long bookId) throws BookException {
		return bookstoreRepository.findById(bookId)
				.orElseThrow(() -> new BookException("Book with bookId" + bookId + "does not exists!!"));
	}

	@Override
	public List<Book> sortBooksByPriceFromLowToHigh() throws BookException {
		List<Book> booksList = getAllBooks();
		return booksList.stream().sorted((firstBook, secondBook) -> (int) (secondBook.price - firstBook.price))
				.collect(Collectors.toList());
	}

	@Override
	public List<Book> sortBooksByPriceFromHighToLow() throws BookException {
		List<Book> booksList = getAllBooks();
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
	public Book updateBookDataByBookId(long bookId, BookDTO bookDTO) throws BookException {
		Book bookData = this.getBookDataByBookId(bookId);
		bookData.updateBookDataByBookId(bookDTO);
		return bookstoreRepository.save(bookData);
	}

	@Override
	public void deleteBookDataByBookId(long bookId) throws BookException {
		Book bookData = this.getBookDataByBookId(bookId);
		bookstoreRepository.delete(bookData);
	}

	public long count() {
		return bookstoreRepository.count();
	}

	@Override
	public List<Book> sortBooksByNewArrivals() {
		return bookstoreRepository.findBookOrderByCreatedDateAndTimeDesc();
	}

}
