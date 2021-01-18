package com.bridgelabz.onlinebookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.onlinebookstore.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Query(value = "select * from cart_items where user_id=:userId", nativeQuery = true)
	public List<Cart> findByUserId(Long userId);
	
	@Query(value = "select * from cart_items where user_id=:userId", nativeQuery = true)
	Cart findByUserIds(Long userId);

	@Query("UPDATE Cart c SET c.orderQuantity=?1 WHERE c.book.bookId=?2 AND c.user.userId=?3")
	@Modifying
	public void updateOrderQuantity(Integer order_quantity, Long bookId, Long userId);

	@Query("DELETE FROM Cart c WHERE c.user.id=?1 AND c.book.bookId=?2")
	@Modifying
	public void deleteByUserAndBook(Long userId, Long bookId);

	@Query(value = "select * from cart_items where  user_id=:userId and book_id=:bookId", nativeQuery = true)
	public Cart findByUserIdAndBookId(Long userId, Long bookId);

	@Query(value = "select book_id from cart_items where book_id=:bookId", nativeQuery = true)
	Long findDuplicateBookId(Long bookId);

}

