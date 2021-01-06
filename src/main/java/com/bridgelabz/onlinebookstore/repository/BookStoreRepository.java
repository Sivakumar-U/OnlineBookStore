package com.bridgelabz.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.onlinebookstore.model.Book;

public interface BookStoreRepository extends JpaRepository<Book, Long> {

	@Query("UPDATE Book SET quantity=?1 WHERE bookId=?2")
	@Modifying
	public void updateQuantityAfterOrder(Integer quantity, Long bookId);

}
