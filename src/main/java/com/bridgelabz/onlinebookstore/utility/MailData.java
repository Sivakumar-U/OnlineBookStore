package com.bridgelabz.onlinebookstore.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Customer;

@Service
public class MailData {

    private String bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    private String Header = "\t\t\t\t\t\t\t\t\tORDER CONFIRMATION\n\n";
    private String shopAdd = "42, 14th Main, 15th Cross, Sector 4 ,opp to BDA complex, near Kumarakom restaurant, HSR Layout, Bangalore 560034";
    private String sincere = "Sincerely,\nBookstore Private Limited\nadmin@booksStore,in\n";
    private String content= "Thank you again for your order.\n\n"+"We are received your order  and will contact you as soon as your package is shipped\n";
    private String acknowledge="We acknowledge the receipt of your purchase order ";

    public String  getOrderMail(Long orderId, Customer customer, double totalPrice, List<Cart> cart) {
        String allBookData = "";
        for (Cart book : cart) {
            allBookData += "BookName "+book.getBook().getBookName() + "\tQuantity " + book.getBook().getQuantity() + "\tBookPrice : Rs." + book.getBook().getQuantity() * book.getBook().getPrice() + "\n";
        }
        String customerDetails="\nShipping Address :"+"\n"+customer.getPhoneNumber()+",\n"+customer.getAddress()+",\n"
                +customer.getCity()+",\n"+customer.getState()+",\n"+customer.getPinCode()+".\n\n";
        return Header + bookingTime + "\n\n" + shopAdd + "Dear  " + customer.getFullName()+ ",\n\n" +
        "Order Number : "+orderId+"\n"+allBookData+"Total Book Price : Rs."+totalPrice+"\n\n"+customerDetails+acknowledge + content + sincere;
}
}


