package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Book;

public interface IBookStoreService {

	List<Book> getAllBooks() throws BookException;

	List<Book> sortBooksByPriceFromLowToHigh();

	List<Book> sortBooksByPriceFromHighToLow();

	Book createBookData(BookDTO bookDTO);

	Book getBookDataByBookId(long bookId);

	Book updateBookDataByBookId(long bookId, @Valid BookDTO bookDTO);

	void deleteBookDataByBookId(long bookId);

	long count();

	List<Book> sortBooksByNewArrivals();

}
