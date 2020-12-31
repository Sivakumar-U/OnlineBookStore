package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
	@Column
	private String _id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private long bookId;
	@Column
	private String authorName;
	@Column
	private String bookName;
	@Column
	private String image;
	@Column
	private int quantity;
	@Column
	public double price;
	@Column
	private String bookDetails;

}
