package com.example.springex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springex.dto.ConfirmationToken;
import com.example.springex.dto.Employee;
import com.example.springex.mail.EmailSenderService;
import com.example.springex.repository.ConfirmationTokenRepository;
import com.example.springex.repository.UserRepository;
import com.example.springex.util.ResponseHandler;

@RestController
public class UserController {

	
	@Autowired
	private UserRepository userRepository;

	@Autowired

	private ConfirmationTokenRepository confirmToken;

	@Autowired
	private EmailSenderService email;

	@PostMapping("/reg")
	public ResponseEntity<Object> register(@RequestBody Employee emp)

	{

		Employee em1 = userRepository.findByEmail(emp.getEmail());
		if (em1 != null) {

			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This email already exists!", emp.getEmail());

		}

		em1 = userRepository.findByMobile(emp.getMobile());
		if (em1 != null) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This mobile number already exists!",
					emp.getEmail());

		}

		userRepository.save(emp);
		ConfirmationToken confirmationToken = new ConfirmationToken(emp);

		confirmToken.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(emp.getEmail());

		mailMessage.setFrom(emp.getEmail());
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:7518/confirm-account?token=" + confirmationToken.getConfirmationToken());

		email.sendEmail(mailMessage);

		return ResponseHandler.generateResponse(HttpStatus.OK, false,
				"Registration sucessFull and mail send register email address", emp);

	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST) 
	public ResponseEntity<Object> confirmUserAccount(@RequestParam("token") String confirmationToken) {

		ConfirmationToken token = confirmToken.findByConfirmationToken(confirmationToken);
		
		
	
		if (token!= null) {
			Employee emp = userRepository.findByEmail(token.getEmp().getEmail());

			emp.setEnabled(true);
			userRepository.save(emp);
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Account verified", null);
		} else {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This link invalid", null);

		}
		
		
		
		

	}
	
	

}
