package com.bridgelabz.onlinebookstore.utility;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Cart;

@Service
public class MailData {

	private String Header = "\t\t\t\t\t\t\t\t\tORDER PLACED SUCCESSFULLY\n";
	private String shopAddress = "42, 14th Main, 15th Cross, Sector 4 ,opp to BDA complex, near Kumarakom restaurant, HSR Layout, Bangalore 560034";
	private String sincere = "Sincerely,\n Online Bookstore Private Limited\nadmin@bookstore.in\n";
	private String content = "Thank you again for your order.\n\n"
			+ "We are received your order  and will contact you as soon as your package is shipped\n";
	private String acknowledge = "We acknowledge the receipt of your purchase order ";

	public String getOrderMail(Long orderId, String customer, double totalPrice, List<Cart> cart) {
		String allBookData = "";
		for (Cart book : cart) {
			allBookData += "Book Name " + book.getBook().getBookName() + "\tQuantity " + book.getBook().getQuantity()
					+ "\tBookPrice : Rs." + book.getBook().getQuantity() * book.getBook().getPrice() + "\n";
		}

		return Header +"\n\n" + shopAddress + "Dear  " + customer + ",\n\n"
				+ "Order Number : " + orderId + "\n" + allBookData + "Total Price : Rs." + totalPrice + "\n\n"
				+ acknowledge + content + sincere;
	}
}
