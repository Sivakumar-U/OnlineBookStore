package com.bridgelabz.onlinebookstore.utility;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Cart;

@Service
public class MailData {

	@Autowired
	private Environment env;

	private String Header = "\t\t\t\t\t\t\t\t\tORDER PLACED SUCCESSFULLY\n";
	private String shopAddress = "42, 14th Main, 15th Cross, Sector 4 ,opp to BDA complex, near Kumarakom restaurant, HSR Layout, Bangalore 560034\n";
	private String sincere = "Sincerely,\nOnline Bookstore Private Limited\nadmin@bookstore.in\n";
	private String content = "Thank you again for your order.\n\n";

	public String getOrderMail(Long orderId, String customer, double totalPrice, List<Cart> cart) {
		String allBookData = "";
		for (Cart book : cart) {
			allBookData += "Book Name : " + book.getBook().getBookName() + "\tQuantity :" + book.getOrderQuantity()
					+ "\tBookPrice : Rs." + book.getBook().getPrice() + "\n";
		}

		return Header + "\n" + " ShopAddress : " + shopAddress + "\nDear  " + customer + ",\n\n" + "Order Number : "
				+ orderId + "\n" + allBookData + "Total Price : Rs." + totalPrice + "\n\n" + content + sincere;
	}

	public void sendMessage(String subject,String emailId, String fullName, String message1, String string, String message2, String mail) {
		final String username = env.getProperty("spring.mail.username");
		final String password = env.getProperty("spring.mail.password");

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject(subject);
			message.setText("\nDear " + fullName + message1 +string + message2 + mail);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
