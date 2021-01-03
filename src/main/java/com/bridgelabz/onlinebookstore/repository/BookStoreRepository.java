package com.bridgelabz.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.onlinebookstore.model.Book;

public interface BookStoreRepository extends JpaRepository<Book, Long> {

}
