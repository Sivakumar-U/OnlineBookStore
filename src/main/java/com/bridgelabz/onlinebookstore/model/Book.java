package com.bridgelabz.onlinebookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bridgelabz.onlinebookstore.dto.BookDTO;

import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;
	@Column
	private String authorName;
	@Column
	private String bookName;
	@Column
	private String image;
	@Column
	private Integer quantity;
	@Column
	public Double price;
	@Column
	private String bookDetails;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date_and_time")
	private Date createdDateAndTime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date_and_time")
	private Date updatedDateAndTime;
	
	public Book() {

	}

	public Book(BookDTO bookDTO) {
		this.updateBookDataByBookId(bookDTO);
	}

	public void updateBookDataByBookId(BookDTO bookDTO) {

		this.authorName = bookDTO.authorName;
		this.bookName = bookDTO.bookName;
		this.image = bookDTO.image;
		this.quantity = bookDTO.quantity;
		this.price = bookDTO.price;
		this.bookDetails = bookDTO.bookDetails;

	}

}
