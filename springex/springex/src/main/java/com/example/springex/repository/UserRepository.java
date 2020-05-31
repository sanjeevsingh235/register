package com.example.springex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springex.dto.Employee;

public interface UserRepository extends JpaRepository<Employee,Integer> {
	
	public Employee findByEmail(String email);
	public Employee findByMobile(String mobile);
	
}
