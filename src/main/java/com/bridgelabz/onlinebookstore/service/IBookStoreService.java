package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Book;

public interface IBookStoreService {

	List<Book> getAllBooks() throws BookException;

	List<Book> sortBooksByPriceFromLowToHigh() throws BookException;

	List<Book> sortBooksByPriceFromHighToLow() throws BookException;

	Book createBookData(BookDTO bookDTO) throws BookException;

	Book getBookDataByBookId(long bookId) throws BookException;

	Book updateBookDataByBookId(long bookId, @Valid BookDTO bookDTO) throws BookException;

	void deleteBookDataByBookId(long bookId) throws BookException;

	long count();

	List<Book> sortBooksByNewArrivals();

}
