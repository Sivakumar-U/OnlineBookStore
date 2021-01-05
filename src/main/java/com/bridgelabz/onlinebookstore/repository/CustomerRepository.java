package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bridgelabz.onlinebookstore.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query(value = "select * from customer where user_id=:userId", nativeQuery = true)
	Optional<Customer> findByUserId(Long userId);
}
