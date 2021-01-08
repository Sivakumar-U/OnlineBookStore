package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.onlinebookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = "SELECT * FROM ordered_items where user_id=:userId", nativeQuery = true)
	Optional<Order> findByUserId(Long userId);

}
