package com.example.springex.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springex.dto.User;
import com.example.springex.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User getEmployeeByName(String name) {
		
		return userRepository.findByName(name);
	}

	

	@Override
	public User DeleteByEmail(String email) {
		
		return userRepository.deleteByEmail(email);
	}



	@Override
	public void deleteUserById(int userid) {
		
		 userRepository.deleteById(userid);
	
		
	}


	@Override
	public User getEmployeeById(int userid) {
		
		return userRepository.getOne(userid);
	}



	@Override
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}



	


	



	
	}
